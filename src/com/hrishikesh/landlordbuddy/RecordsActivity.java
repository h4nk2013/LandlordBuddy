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

public class RecordsActivity extends Activity {
	private Button mHistogram, mPieChart;
	private SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
		mHistogram = (Button) findViewById(R.id.histogram);
		mPieChart = (Button) findViewById(R.id.piechart);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
		
		mHistogram.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				histoGram();
			}
		});
		
		mPieChart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pieChart();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.records, menu);
		return true;
	}
	
	public void pieChart(){

		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		PieChartFragment pieChartFragment = new PieChartFragment();
		ft.replace(R.id.graphics, pieChartFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	public void histoGram(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		HistogramFragment histogramFragment = new HistogramFragment();
		ft.replace(R.id.graphics, histogramFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		if (itemId == R.id.action_add) {
			Intent i = new Intent(RecordsActivity.this, AddNewEntry.class);
			startActivity(i);
			return true;
		}
		else if (itemId == R.id.action_records) {
			Intent records = new Intent(RecordsActivity.this,RecordsActivity.class);
			startActivity(records);
			return true;
		}
		else if (itemId == R.id.action_export_app_data) {
			Intent export = new Intent(RecordsActivity.this,CsvActivity.class);
			startActivity(export);
			return true;
		} else if (itemId == R.id.action_about) {
			Intent aboutIntent = new Intent(RecordsActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		} else if (itemId == android.R.id.home) {
			finish();
			return true;
		} else if (itemId == R.id.action_logout) {
			Intent logoutIntent = new Intent(RecordsActivity.this, LandlordBuddyMainActivity.class);
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
