package com.hrishikesh.landlordbuddy;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TenantDetailDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private String[] allColumns = {DatabaseHelper.KEY_YEAR, DatabaseHelper.KEY_MONTH, DatabaseHelper.KEY_RENT, DatabaseHelper.KEY_PAID};
	public TenantDetailDataSource(Context context) {
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
     * Creating a TenantDetail
     */
	public TenantDetailModel createTenantDetail(String year, String month, String rent, String paid, String due, long tenant_id){
		ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_YEAR, year);
        values.put(DatabaseHelper.KEY_MONTH, month);
        values.put(DatabaseHelper.KEY_RENT, rent);
        values.put(DatabaseHelper.KEY_PAID, paid);
        values.put(DatabaseHelper.KEY_DUE, due);
        values.put(DatabaseHelper.TENANT_ID, tenant_id);
        
        long insertId = database.insert(DatabaseHelper.TABLE_TENANTDETAILS, null, values);
        
        Cursor cursor = database.query(DatabaseHelper.TABLE_TENANTDETAILS, allColumns, DatabaseHelper.KEY_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TenantDetailModel tenantDetailModel = getTenantDetail(insertId);
        cursor.close();
        return tenantDetailModel;

	}
	public TenantDetailModel getTenantDetail(long tenant_id) {
	    SQLiteDatabase db = databaseHelper.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_TENANTDETAILS + " WHERE "
	            + DatabaseHelper.KEY_ID + " = " + tenant_id;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();
	 
	    TenantDetailModel tenantDetailModel = new TenantDetailModel();
	    String rent = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RENT));
	    String paid = c.getString(c.getColumnIndex(DatabaseHelper.KEY_PAID));
	    System.out.println(rent + paid);
	    tenantDetailModel.setId(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_ID))));
	    tenantDetailModel.setYear((c.getString(c.getColumnIndex(DatabaseHelper.KEY_YEAR))));
	    tenantDetailModel.setMonth((c.getString(c.getColumnIndex(DatabaseHelper.KEY_MONTH))));
	    tenantDetailModel.setRent(Integer.valueOf(rent));
	    tenantDetailModel.setPaid(Integer.valueOf(paid));
	    
	    
	    return tenantDetailModel;
	}
	public List<TenantDetailModel> generateDetailList(long tenant_id){
		List<TenantDetailModel> tenantModels = new ArrayList<TenantDetailModel>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor  c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TENANTDETAILS + " WHERE " + DatabaseHelper.TENANT_ID + " = " + tenant_id ,null);
		
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			String rent = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RENT));
		    String paid = c.getString(c.getColumnIndex(DatabaseHelper.KEY_PAID));
		    String due = c.getString(c.getColumnIndex(DatabaseHelper.KEY_DUE));
			TenantDetailModel tenantDetailModel = new TenantDetailModel();
			tenantDetailModel.setId(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_ID))));
		    tenantDetailModel.setYear((c.getString(c.getColumnIndex(DatabaseHelper.KEY_YEAR))));
		    tenantDetailModel.setMonth((c.getString(c.getColumnIndex(DatabaseHelper.KEY_MONTH))));
		    tenantDetailModel.setPaid(Integer.valueOf(rent));
		    tenantDetailModel.setRent(Integer.valueOf(paid));
		    tenantDetailModel.setDue(Integer.valueOf(due));
            tenantModels.add(tenantDetailModel);
            c.moveToNext();
        }
		
		return tenantModels;
	}
	public TenantDetailModel updateTenantDetails(int mid, String year, String month, String rent, String paid, String due){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.KEY_YEAR, year);
		contentValues.put(DatabaseHelper.KEY_MONTH, month);
		contentValues.put(DatabaseHelper.KEY_RENT, rent);
		contentValues.put(DatabaseHelper.KEY_PAID, paid);
		contentValues.put(DatabaseHelper.KEY_DUE, due);
		
		long id = database.update(DatabaseHelper.TABLE_TENANTDETAILS, contentValues, DatabaseHelper.KEY_ID + "= ?", new String[] {String.valueOf(mid)});
		TenantDetailModel tenantDetailModel = getTenantDetail(id);
		
		return tenantDetailModel;
	}

	public List<TenantDetailModel> generateList() {
		// TODO Auto-generated method stub
		List<TenantDetailModel> tenantModels = new ArrayList<TenantDetailModel>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor  c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TENANTDETAILS ,null);
		
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			String rent = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RENT));
		    String paid = c.getString(c.getColumnIndex(DatabaseHelper.KEY_PAID));
		    String due = c.getString(c.getColumnIndex(DatabaseHelper.KEY_DUE));
			TenantDetailModel tenantDetailModel = new TenantDetailModel();
			tenantDetailModel.setId(Integer.valueOf(c.getString(c.getColumnIndex(DatabaseHelper.KEY_ID))));
		    tenantDetailModel.setYear((c.getString(c.getColumnIndex(DatabaseHelper.KEY_YEAR))));
		    tenantDetailModel.setMonth((c.getString(c.getColumnIndex(DatabaseHelper.KEY_MONTH))));
		    tenantDetailModel.setPaid(Integer.valueOf(rent));
		    tenantDetailModel.setRent(Integer.valueOf(paid));
		    tenantDetailModel.setDue(Integer.valueOf(due));
            tenantModels.add(tenantDetailModel);
            c.moveToNext();
        }
		
		return tenantModels;
	}
	
	public void deleteDetail(long tenant_id) {
	    SQLiteDatabase db = databaseHelper.getWritableDatabase();
	    db.delete(DatabaseHelper.TABLE_TENANTDETAILS, DatabaseHelper.KEY_ID + " = ?",
	            new String[] { String.valueOf(tenant_id) });
	}

	
	
}
