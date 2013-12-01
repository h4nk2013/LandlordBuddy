package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class AddNewEntry extends Activity {

	private Button mSaveButton;
	SessionManager session;
	private TenantDataSource tenantDataSource;
	private EditText mName, mEmail, mAddress, mPhone, mDeposit;
	private Intent intent;
	private TenantModel tenantModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_entry);
		mSaveButton = (Button) findViewById(R.id.save_button);
		mSaveButton.setText("Save");
		tenantDataSource = new TenantDataSource(AddNewEntry.this);
		mName = (EditText) findViewById(R.id.TenantName);
		mEmail = (EditText) findViewById(R.id.TenantEmail);
		mAddress = (EditText) findViewById(R.id.TenantAddress);
		mPhone = (EditText) findViewById(R.id.TenantPhone);
		mDeposit = (EditText) findViewById(R.id.TenantDeposit);
		intent = getIntent();
		if(intent.getIntExtra("id",0) > 0){
			tenantModel = tenantDataSource.getTenant((intent.getIntExtra("id",0)));
			mName.setText(tenantModel.getName().toString());
			mAddress.setText(tenantModel.getAddress().toString());
			mEmail.setText(tenantModel.getEmail().toString());
			mDeposit.setText(""+tenantModel.getDeposit());
			mPhone.setText(tenantModel.getPhone().toString());
			mSaveButton.setText("Update");
		}
		
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
		
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tenantDataSource.open();
				if(intent.getIntExtra("id",0) > 0){
//					TenantDataSource tenantDataSource = new TenantDataSource(AddNewEntry.this);
//					TenantModel tenantModel = tenantDataSource.getTenant(Long.parseLong(intent.getStringExtra("id")));
//					mName.setText(tenantModel.getName().toString());
//					mAddress.setText(tenantModel.getAddress().toString());
//					mEmail.setText(tenantModel.getEmail().toString());
//					mDeposit.setText(""+tenantModel.getDeposit());
//					mPhone.setText(tenantModel.getPhone().toString());
					tenantDataSource.updateTenant(intent.getIntExtra("id",0),
												  mName.getText().toString(),
												  mAddress.getText().toString(),
												  mPhone.getText().toString(),
												  mEmail.getText().toString(),
												  mDeposit.getText().toString());
				}
				else {
					TenantModel tm = tenantDataSource.createTenant(mName.getText().toString(),
															mAddress.getText().toString(),
															mPhone.getText().toString(),
															mEmail.getText().toString(),
															mDeposit.getText().toString());
				}	
				tenantDataSource.close();
				Intent i = new Intent(AddNewEntry.this, HomeActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_entry, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(AddNewEntry.this, LandlordBuddyMainActivity.class);
			startActivity(logoutIntent);
			session.logoutUser();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
