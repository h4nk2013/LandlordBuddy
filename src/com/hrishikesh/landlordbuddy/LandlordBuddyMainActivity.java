package com.hrishikesh.landlordbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;



public class LandlordBuddyMainActivity extends Activity {

	
	// Session Manager Class
    SessionManager session;
    private Button mContinueButton;
    private EditText mUserName, mEmailAddress;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landlord_buddy_main);
		setupUI(findViewById(R.id.parent));
		mContinueButton = (Button) findViewById(R.id.continue_button);
		mUserName = (EditText) findViewById(R.id.username);
		mEmailAddress = (EditText) findViewById(R.id.emailAddress);
		
		// Session Manager
        session = new SessionManager(getApplicationContext());
		
		mContinueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get username, password from EditText
                String username = mUserName.getText().toString();
                String emailAddress = mEmailAddress.getText().toString();
                
             // Check if username, password is filled               
                if(username.trim().length() > 0 && emailAddress.trim().length() > 0){
                    
                    if(username.equals(username) && emailAddress.equals(emailAddress)){

                        session.createLoginSession(username, emailAddress);
                         
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), StartupHomeActivity.class);
                        startActivity(i);                         
                    }else{
                    	 // fullname / emailAddress doesn't match
                    	Builder alert = new AlertDialog.Builder(LandlordBuddyMainActivity.this);
                    	alert.setTitle("Login Failed!!");
                    	alert.setMessage("Name and Email are incorrect");
                    	alert.setPositiveButton("OK",null); alert.show();
                    }
                   }else{ 
                	   // user didn't entered fullname or emailAddress // Show alert asking him to enter the details
                	   Builder alert = new AlertDialog.Builder(LandlordBuddyMainActivity.this);
                	   alert.setTitle("Login Failed!!");
                	   alert.setMessage("Please enter Name and Email Address"); 
                	   alert.setPositiveButton("OK",null); alert.show(); 
                }

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landlord_buddy_main, menu);
		return true;
	}
	public static void hideSoftKeyboard(View v) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            @Override
				public boolean onTouch(View v, MotionEvent event) {
	                hideSoftKeyboard(v);
	                return false;
	            }

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(LandlordBuddyMainActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == R.id.action_exit) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
