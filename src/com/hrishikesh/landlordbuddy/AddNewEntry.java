package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
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
	private String valid_name, valid_email;
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
		mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		mName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Is_Valid_Person_Name(mName); // pass your EditText Obj here.
			}
		});
		
		mEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Is_Valid_Email(mEmail);  // pass your EditText Obj here.
			}
		});
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tenantDataSource.open();
//				if(mName.getText().equals("") || mAddress.getText().equals("") || mPhone.getText().equals("") || mEmail.getText().equals("") || mDeposit.getText().equals(""))
				if(intent.getIntExtra("id",0) > 0){
					tenantDataSource.updateTenant(intent.getIntExtra("id",0),
												  mName.getText().toString(),
												  mAddress.getText().toString(),
												  mPhone.getText().toString(),
												  mEmail.getText().toString(),
												  mDeposit.getText().toString());
					tenantDataSource.close();
					Intent i = new Intent(AddNewEntry.this, HomeActivity.class);
					startActivity(i);
				}
				else if(mName.getText().length() == 0 || 
						mAddress.getText().length() == 0 || 
						mPhone.getText().length() == 0 || 
						mEmail.getText().length() == 0 || 
						mDeposit.getText().length() == 0){
					new AlertDialog.Builder(AddNewEntry.this)
				    .setTitle("Incomplete !!!")
				    .setMessage("Please Complete the form")
				    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				     .show();
				}
				else{
					tenantDataSource.createTenant(mName.getText().toString(),
															mAddress.getText().toString(),
															mPhone.getText().toString(),
															mEmail.getText().toString(),
															mDeposit.getText().toString());
					tenantDataSource.close();
					Intent i = new Intent(AddNewEntry.this, HomeActivity.class);
					startActivity(i);
				}	
			}
		});
	}
	
	public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
		if (edt.getText().toString().length() <= 0) {
			edt.setError("Enter Valid Name");
			valid_name = null;
		} else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
			edt.setError("Enter Valid Name");
			valid_name = null;
		} else {
			valid_name = edt.getText().toString();
		}

	}
	public void Is_Valid_Email(EditText edt) {
		if (edt.getText().toString() == null) {
			edt.setError("Invalid Email Address");
			valid_email = null;
		} else if (isEmailValid(edt.getText().toString()) == false) {
			edt.setError("Invalid Email Address");
			valid_email = null;
		} else {
			valid_email = edt.getText().toString();
		}
	}

	boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	} // end of email matcher
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
		} else if (itemId == R.id.action_records) {
			Intent records = new Intent(AddNewEntry.this,RecordsActivity.class);
			startActivity(records);
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(AddNewEntry.this, LandlordBuddyMainActivity.class);
			startActivity(logoutIntent);
			session.logoutUser();
			return true;
		} else if (itemId == R.id.action_export_app_data) {
			Intent export = new Intent(AddNewEntry.this,CsvActivity.class);
			startActivity(export);
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
