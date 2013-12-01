package com.hrishikesh.landlordbuddy;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StartupHomeActivity extends Activity {
	
	SessionManager session;
	private TextView mWelcomeMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup_home);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        mWelcomeMessage =(TextView) findViewById(R.id.welcome_message);
        
     // get user data from session
        HashMap<String, String> user = session.getUserDetails();
         
        // name
        String name = user.get(SessionManager.KEY_NAME);
        mWelcomeMessage.setText("Hello "+name);
        
         
        // email
        String email = user.get(SessionManager.KEY_EMAIL);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startup_home, menu);
		return super.onCreateOptionsMenu(menu);
		

		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
		if (itemId == R.id.action_add) {
			Intent i = new Intent(StartupHomeActivity.this, AddNewEntry.class);
			startActivity(i);
			return true;
		} else if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(StartupHomeActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == R.id.action_exit) {
			finish();
			return true;
		} else if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(StartupHomeActivity.this, LandlordBuddyMainActivity.class);
			startActivity(logoutIntent);
			session.logoutUser();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
