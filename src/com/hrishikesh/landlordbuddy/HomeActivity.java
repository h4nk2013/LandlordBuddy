package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;



public class HomeActivity extends Activity {
//	private TenantDataSource tenantDataSource;
	private ListView mList;
	private SessionManager session;
	private TenantDataSource tenantDataSource;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private TenantModel item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
    	mList = (ListView) findViewById(R.id.list);
		tenantDataSource = new TenantDataSource(HomeActivity.this);
		ListViewArrayAdapter adapter = new ListViewArrayAdapter(HomeActivity.this, tenantDataSource.generateList());
		mList.setAdapter(adapter);
		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

		      @Override
		      public boolean onItemLongClick(AdapterView<?> parent, View view,
		          int position, long id) {
		        item = (TenantModel) parent.getAdapter().getItem(position);
		        new AlertDialog.Builder(HomeActivity.this)
			    .setTitle(item.getName())
			    .setMessage("Select Option")
			    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			      .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	new AlertDialog.Builder(HomeActivity.this).setMessage("Do you want to Delete ?")
			        	.setNegativeButton("No", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // do nothing
					        }
					     })
					     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								tenantDataSource.open();
								tenantDataSource.deleteTenant(item.getId());
								tenantDataSource.close();
								Intent i = new Intent(HomeActivity.this,HomeActivity.class);
								startActivity(i);
							}
						})
						.show();
			        	
			        }
			     }) 
			      .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
						Intent i = new Intent(HomeActivity.this,AddNewEntry.class);
						i.putExtra("id", item.getId());
						startActivity(i);
			        }
			     })
			     .show();
		        return true;
		      }
		    });
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
				Intent i = new Intent(HomeActivity.this, UserDetailActivity.class);
				i.putExtra("ID", id);
				startActivity(i);
				
			}
		});

		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

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
		if (itemId == R.id.action_add) {
			Intent i = new Intent(HomeActivity.this, AddNewEntry.class);
			startActivity(i);
			return true;
		}
		else if (itemId == R.id.action_records) {
			Intent records = new Intent(HomeActivity.this,RecordsActivity.class);
			startActivity(records);
			return true;
		}
		else if (itemId == R.id.action_export_app_data) {
			Intent export = new Intent(HomeActivity.this,CsvActivity.class);
			startActivity(export);
			return true;
		} else if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(HomeActivity.this, LandlordBuddyMainActivity.class);
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
