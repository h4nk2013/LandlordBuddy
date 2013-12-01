package com.hrishikesh.landlordbuddy;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TenantDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private String[] allColumns = {DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_ADDRESS, DatabaseHelper.KEY_PHONE, DatabaseHelper.KEY_EMAIL};
	public TenantDataSource(Context context) {
		// TODO Auto-generated constructor stub
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException{
		// TODO Auto-generated method stub
		database = databaseHelper.getWritableDatabase();
	}
	
	public void close() {
		// TODO Auto-generated method stub
		databaseHelper.close();
	}
	
	/*
     * Creating a Tenant
     */
	
	public TenantModel createTenant (String name, String address, String phone, String email, String deposit){
		ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_ADDRESS, address);
        values.put(DatabaseHelper.KEY_PHONE, phone);
        values.put(DatabaseHelper.KEY_EMAIL, email);
        values.put(DatabaseHelper.KEY_DEPOSIT, deposit);
        long insertId = database.insert(DatabaseHelper.TABLE_TENANT, null, values);
        
        Cursor cursor = database.query(DatabaseHelper.TABLE_TENANT, allColumns, DatabaseHelper.KEY_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TenantModel tenantModel = getTenant(insertId);
        cursor.close();
        return tenantModel;

	}
	
	/*
	 * get single tenant
	 */
	public TenantModel getTenant(long tenant_id) {
	    SQLiteDatabase db = databaseHelper.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_TENANT + " WHERE "
	            + DatabaseHelper.KEY_ID + " = " + tenant_id;
	    TenantModel tenantModel = new TenantModel();
	    Cursor c = db.rawQuery(selectQuery, null);
	    if (c.getCount() > 0 ){
	    	if (c != null)
		        c.moveToFirst();
			tenantModel.setId(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_ID))));
		    tenantModel.setName((c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME))));
		    tenantModel.setAddress((c.getString(c.getColumnIndex(DatabaseHelper.KEY_ADDRESS))));
		    tenantModel.setEmail((c.getString(c.getColumnIndex(DatabaseHelper.KEY_EMAIL))));
		    tenantModel.setPhone((c.getString(c.getColumnIndex(DatabaseHelper.KEY_PHONE))));	
		    tenantModel.setDeposit(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_DEPOSIT))));
	    }
	        
	    
	    return tenantModel;
	}
	
	public List<TenantModel> generateList(){
		List<TenantModel> tenantModels = new ArrayList<TenantModel>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor  c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TENANT,null);
		
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			TenantModel tenantModel = new TenantModel();
			tenantModel.setId(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_ID))));
		    tenantModel.setName((c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME))));
		    tenantModel.setAddress((c.getString(c.getColumnIndex(DatabaseHelper.KEY_ADDRESS))));
		    tenantModel.setEmail((c.getString(c.getColumnIndex(DatabaseHelper.KEY_EMAIL))));
		    tenantModel.setPhone((c.getString(c.getColumnIndex(DatabaseHelper.KEY_PHONE))));
		    tenantModel.setDeposit(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_DEPOSIT))));
            tenantModels.add(tenantModel);
            c.moveToNext();
        }
		
		return tenantModels;
	}
	public void deleteTenant(long tenant_id) {
	    SQLiteDatabase db = databaseHelper.getWritableDatabase();
	    db.delete(DatabaseHelper.TABLE_TENANT, DatabaseHelper.KEY_ID + " = ?",
	            new String[] { String.valueOf(tenant_id) });
	}
	
	public TenantModel updateTenant(int mid, String name, String address, String phone, String email, String deposit){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.KEY_NAME, name);
		contentValues.put(DatabaseHelper.KEY_ADDRESS, address);
		contentValues.put(DatabaseHelper.KEY_EMAIL, phone);
		contentValues.put(DatabaseHelper.KEY_PHONE, email);
		contentValues.put(DatabaseHelper.KEY_DEPOSIT, deposit);
		
		long id = database.update(DatabaseHelper.TABLE_TENANT, contentValues, DatabaseHelper.KEY_ID + "= ?", new String[] {String.valueOf(mid)});
		TenantModel tenantModel2 = getTenant(id);
		
		return tenantModel2;
	}
	
//	private TenantModel cursorToTenant(Cursor c, long id){
//		TenantModel tenantModel = new TenantModel();
//		tenantModel.setId((int) id);
//	    tenantModel.setName((c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME))));
//	    tenantModel.setAddress((c.getString(c.getColumnIndex(DatabaseHelper.KEY_ADDRESS))));
//	    tenantModel.setEmail((c.getString(c.getColumnIndex(DatabaseHelper.KEY_EMAIL))));
//	    tenantModel.setPhone((c.getString(c.getColumnIndex(DatabaseHelper.KEY_PHONE))));
//		
//		return tenantModel;
//	}
}
