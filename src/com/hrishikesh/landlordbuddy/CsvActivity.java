package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CsvActivity extends Activity {
	private SessionManager session;
	private Button dbExport, detailsExport, tenantsExport;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_csv);
		
		dbExport = (Button) findViewById(R.id.dbExport);
		detailsExport = (Button) findViewById(R.id.detailsExport);
		tenantsExport = (Button) findViewById(R.id.tenantExport);
		
		dbExport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				importDatabase();
			}
		});
		detailsExport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				importDetails();
			}
		});
		tenantsExport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				importTenant();
			}
		});
		
		
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        
	}
	
	public void importDetails(){

		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		TenantDetailExportFragment tenantDetailExportFragment = new TenantDetailExportFragment();
		ft.replace(R.id.csvLayout, tenantDetailExportFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	public void importTenant(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		TenantExportFragment tenantExportFragment = new TenantExportFragment();
		ft.replace(R.id.csvLayout, tenantExportFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	public void importDatabase(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ExportDatabaseFragment exportDatabaseFragment = new ExportDatabaseFragment();
		ft.replace(R.id.csvLayout, exportDatabaseFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.csv, menu);
		return true;	
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(CsvActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == R.id.action_records) {
			Intent records = new Intent(CsvActivity.this,RecordsActivity.class);
			startActivity(records);
			return true;
		} else if (itemId == R.id.action_exit) {
			finish();
			return true;
		} else if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(CsvActivity.this, LandlordBuddyMainActivity.class);
			startActivity(logoutIntent);
			session.logoutUser();
			return true;
		} else if (itemId == R.id.action_exit) {
			moveTaskToBack(true);
			this.finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
