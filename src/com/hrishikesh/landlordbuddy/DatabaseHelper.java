package com.hrishikesh.landlordbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
//    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    public static final String DATABASE_NAME = "LandLordBuddy";
 
    // Table Names
    public static final String TABLE_TENANT = "tenants";
    public static final String TABLE_TENANTDETAILS = "tenantDetails";
    public static final String TABLE_TENANT_TENANTDETAILS = "tenants_tenantDetails";
 
    // Common column names
    public static final String KEY_ID = "_id";
    public static final String KEY_CREATED_AT = "created_at";
 
    // Tenants Table - column names
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DEPOSIT = "deposit";
 
    // tenantDetails Table - column names
    public static final String KEY_YEAR = "year";
    public static final String KEY_MONTH = "month";
    public static final String KEY_RENT = "rent";
    public static final String KEY_PAID = "paid";
    public static final String KEY_DUE = "due";
    public static final String TENANT_ID = "tenant_id";
 
    // tenants_tenantDetails Table - column names
    public static final String KEY_TENANT_ID = "tenant_id";
    public static final String KEY_TENANTDETAIL_ID = "tenantDetails_id";
 
    // Table Create Statements
    
    // Tenant table create statement 
    private static final String CREATE_TABLE_TENANT = "CREATE TABLE IF NOT EXISTS "
    		+ TABLE_TENANT + "(" + KEY_ID + " INTEGER PRIMARY KEY, "+ KEY_NAME
    		+ " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PHONE + " TEXT,"
    		+ KEY_EMAIL + " TEXT, " + KEY_DEPOSIT + " TEXT" +")";
    
    // TENANTDETAILS Table create statement
    private static final String CREATE_TABLE_DETAILS = "CREATE TABLE IF NOT EXISTS "
    		+ TABLE_TENANTDETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_YEAR + " TEXT," + KEY_MONTH
    		+ " TEXT," + KEY_RENT + " TEXT," + KEY_PAID + " TEXT," + KEY_DUE + " TEXT," + TENANT_ID + " TEXT" + ")";
     
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_TENANT);
        db.execSQL(CREATE_TABLE_DETAILS);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TENANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TENANTDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TENANT_TENANTDETAILS);
 
        // create new tables
        onCreate(db);
    }
    
    
    
    
    
    /*
     * Creating tenant detail
     */
    public long createTag(TenantDetailModel detail) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_MONTH, detail.getMonth());
        values.put(KEY_RENT, detail.getRent());
        values.put(KEY_PAID, detail.getPaid());
     
        // insert row
        long detail_id = db.insert(TABLE_TENANT_TENANTDETAILS, null, values);
     
        return detail_id;
    }
    
    /*
     * Creating Tenant_DETAIL_RELATION
     */
    public long createTenantDetailsRelation(long tenant_id, long tenant_detail_id) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TENANT_ID, tenant_id);
        values.put(KEY_TENANTDETAIL_ID, tenant_detail_id);
 
        long id = db.insert(TABLE_TENANT_TENANTDETAILS, null, values);
 
        return id;
    }
}