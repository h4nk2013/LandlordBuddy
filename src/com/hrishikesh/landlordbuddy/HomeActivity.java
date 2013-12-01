package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;



public class HomeActivity extends Activity {
	
	private ListView mList;
	SessionManager session;
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		mList = (ListView) findViewById(R.id.list);
		
//		ArrayAdapter<TenantModel> adapter = new ArrayAdapter<TenantModel>(this, android.R.layout.simple_list_item_1,TenantModel.generateList());
		TenantDataSource t1 = new TenantDataSource(HomeActivity.this);
		
		ListViewArrayAdapter adapter = new ListViewArrayAdapter(this, t1.generateList());
		
		mList.setAdapter(adapter);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorListener = new ShakeEventListener();   

	    mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

	      public void onShake() {
	        Toast.makeText(HomeActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
	        
	        finish();
	        startActivity(getIntent());
	      }
	    });
		
		mList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
				TenantModel item = (TenantModel) arg0.getAdapter().getItem(arg2);
				int id = item.getId();
				
//				Toast.makeText(HomeActivity.this, name.getDate(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(HomeActivity.this, UserDetailActivity.class);
				i.putExtra("ID", id);
//				i.putExtra("Date", date);
//				i.putExtra("Due", due);
//				i.putExtra("Paid", paid);
				startActivity(i);
				
			}
		});

		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
		
//		mListItemButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(HomeActivity.this, UserDetailActivity.class);
//				startActivity(i);
//			}
//		});
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == R.id.action_export_app_data) {
			Toast.makeText(this, "Backup Created", Toast.LENGTH_SHORT).show();
			Intent export = new Intent(HomeActivity.this,CsvActivity.class);
			startActivity(export);
			return true;
		} else if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == R.id.action_exit) {
			finish();
			return true;
		} else if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(HomeActivity.this, LandlordBuddyMainActivity.class);
			startActivity(logoutIntent);
			session.logoutUser();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
