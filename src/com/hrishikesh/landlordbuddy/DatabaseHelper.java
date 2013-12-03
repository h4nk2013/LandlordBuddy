package com.hrishikesh.landlordbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
	// private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    public static final String DATABASE_NAME = "LandLordBuddy";
 
    // Table Names
    public static final String TABLE_TENANT = "tenants";
    public static final String TABLE_TENANTDETAILS = "tenantDetails";
    
    // Common column names
    public static final String KEY_ID = "_id";
 
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
 
        // create new tables
        onCreate(db);
    }    
}