package com.hrishikesh.landlordbuddy;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class UserDetailActivity extends Activity implements OnClickListener{
        private Button mNotifyButton;
        private Button mDeleteButton;
        private Button mSave;
        private TextView mName, mAddress, mPhone, mEmail, mDue, mPaid;
        private GoogleMap mMap;
        private SessionManager session;
        private Spinner mMonth;
        private EditText mYear, mPaidEdit, mRent, mDueEdit;
        private ListView reportList;
        private TenantModel tenantModel;
        private TenantDataSource tenantDataSource;
        private TenantDetailDataSource tenantDetailDataSource;
        private long mId;
        private String result;
        private String latitude, longitude;
        private TabHost th;
        private TenantDetailModel item = null;
        public UserDetailActivity(){}
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_detail);
                tenantDetailDataSource = new TenantDetailDataSource(this);
                tenantDataSource = new TenantDataSource(this);
                mDueEdit = (EditText) findViewById(R.id.due_id);
                mYear = (EditText) findViewById(R.id.year_id);
                mPaidEdit = (EditText) findViewById(R.id.paid_id);
                mRent = (EditText) findViewById(R.id.rent_id);
                
                mDue = (TextView) findViewById(R.id.due);
                mPaid = (TextView) findViewById(R.id.paid);
                // Session Manager
		        session = new SessionManager(getApplicationContext());
		        session.checkLogin();
                reportList = (ListView) findViewById(R.id.reportList);
        
                th = (TabHost) findViewById(R.id.tabhost);
                th.setup();
                TabSpec specs = th.newTabSpec("tag1");
                specs.setContent(R.id.details_tab);
                specs.setIndicator("Details");
                th.addTab(specs);
                specs = th.newTabSpec("tag2");
                specs.setContent(R.id.records_tab);
                specs.setIndicator("Records");
                th.addTab(specs);
                specs = th.newTabSpec("tag3");
                specs.setContent(R.id.paid_tab);
                specs.setIndicator("Add Month");
                th.addTab(specs);
                th.setOnTabChangedListener(new OnTabChangeListener() {
                        @Override
                        public void onTabChanged(String tabId) {
                                // TODO Auto-generated method stub
                                if(tabId.equals("tag1") || tabId.equals("tag2")){
                                        item = null;
                                        mYear.setText("");
                                        mRent.setText("");
                                        mPaidEdit.setText("");
                                        mDueEdit.setText("");
                                        mMonth.setSelection(0);
                                        mSave.setText("Save");
                                }
                        }
                });
                mDueEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
                        
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                                // TODO Auto-generated method stub
                        		int rent = 0, due = 0;
                        		if(mRent.getText().length() == 0){
                        			rent = 0;
                        		}else{
                        			rent = Integer.parseInt(mRent.getText().toString());
                        		}
                        		if(mPaidEdit.getText().length() == 0){
                        			due = 0;
                        		}else{
                        			due = Integer.parseInt(mPaidEdit.getText().toString());
                        		}
                                int x = rent - due ;
                                mDueEdit.setText(""+x);
                        }
                });
                
                Intent get = getIntent();
                mName = (TextView)findViewById(R.id.name);
                mPhone = (TextView)findViewById(R.id.phone);
                mEmail = (TextView)findViewById(R.id.email);
                mAddress = (TextView)findViewById(R.id.address);
//                mPaid = (TextView)findViewById(R.id.paid);
//                mDue = (TextView)findViewById(R.id.due);
//                mDate = (TextView)findViewById(R.id.dueDate);
                if(get != null){
                        mId = get.getIntExtra("ID", 0);
                        tenantDataSource.open();
                        tenantModel = tenantDataSource.getTenant(mId);
                        mName.setText(tenantModel.getName());
                        mAddress.setText(tenantModel.getAddress());
                        mPhone.setText(tenantModel.getPhone());
                        mEmail.setText(tenantModel.getEmail());
                        tenantDataSource.close();
                }        
                TenantDetailDataSource t1 = new TenantDetailDataSource(UserDetailActivity.this);
                List<TenantDetailModel> list = t1.generateDetailList(mId);
                TableRowArrayAdapter adapter = new TableRowArrayAdapter(this, list);
                int totalDue=0, totalPaid = 0;
                for(TenantDetailModel t : list){
                	totalDue = totalDue + t.getDue();
                	totalPaid = totalPaid + t.getPaid();
                }
                mPaid.setText("Total Paid $"+ Integer.toString(totalPaid));
                mDue.setText("Total Due $"+ Integer.toString(totalDue));
                reportList.setAdapter(adapter);
                
                reportList.setOnItemLongClickListener(new OnItemLongClickListener() {

      		      @Override
      		      public boolean onItemLongClick(AdapterView<?> parent, View view,
      		          int position, long id) {
      		    	  item = (TenantDetailModel) parent.getAdapter().getItem(position);
      		    	new AlertDialog.Builder(UserDetailActivity.this).setMessage("Do you want to Delete ?")
		        	.setNegativeButton("No", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

			                TenantDetailDataSource t2 = new TenantDetailDataSource(UserDetailActivity.this);
							t2.deleteDetail(item.getId());
                            finish();
                            startActivity(getIntent());
						}
					})
					.show();

						return true;
      		      }
      		    	  
      		      });
                
                reportList.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                                item = (TenantDetailModel) arg0.getAdapter().getItem(arg2);
                                th.setCurrentTabByTag("tag3");
                                mYear.setText(item.getYear().toString());
                                mRent.setText(Integer.toString(item.getRent()));
                                mPaidEdit.setText(Integer.toString(item.getPaid()));
                                mDueEdit.setText(Integer.toString(item.getDue()));
                                if(item.getMonth().equals("January")){
                                        mMonth.setSelection(0);
                                }else if(item.getMonth().equals("February")){
                                        mMonth.setSelection(1);
                                }else if(item.getMonth().equals("March")){
                                        mMonth.setSelection(2);
                                }else if(item.getMonth().equals("April")){
                                        mMonth.setSelection(3);
                                }else if(item.getMonth().equals("May")){
                                        mMonth.setSelection(4);
                                }else if(item.getMonth().equals("June")){
                                        mMonth.setSelection(5);
                                }else if(item.getMonth().equals("July")){
                                        mMonth.setSelection(6);
                                }else if(item.getMonth().equals("August")){
                                        mMonth.setSelection(7);
                                }else if(item.getMonth().equals("September")){
                                        mMonth.setSelection(8);
                                }else if(item.getMonth().equals("October")){
                                        mMonth.setSelection(9);
                                }else if(item.getMonth().equals("November")){
                                        mMonth.setSelection(10);
                                }else{
                                        mMonth.setSelection(12);
                                }
                                mSave.setText("Update");
                        }
                });
                
                mNotifyButton = (Button) findViewById(R.id.notify_button);
                mNotifyButton.setOnClickListener(this);
                
                mDeleteButton = (Button) findViewById(R.id.delete_button);
                mDeleteButton.setOnClickListener(this);
                
                mSave = (Button) findViewById(R.id.save_button);

                mMonth = (Spinner) findViewById(R.id.spinner_month);
                mSave.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // TODO Auto-generated method stub
                                tenantDetailDataSource.open();
                                if(mRent.getText().length() == 0 || mPaidEdit.getText().length() == 0 || mDueEdit.getText().length() == 0){
                                	new AlertDialog.Builder(UserDetailActivity.this).setMessage("Complete the form !!!")
            			        	.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            					        public void onClick(DialogInterface dialog, int which) { 
            					            // do nothing
            					        }
            					     })
            					     .show();
                                }
                                else {
                                	if(item == null){
                                        tenantDetailDataSource.createTenantDetail(mYear.getText().toString(),
                                                                                                                         mMonth.getSelectedItem().toString(),
                                                                                                                         mRent.getText().toString(),
                                                                                                                         mPaidEdit.getText().toString(),
                                                                                                                         mDueEdit.getText().toString(),
                                                                                                                         mId);
                                }
                                else{
                                        tenantDetailDataSource.updateTenantDetails(item.getId(), mYear.getText().toString(),
                                                                                                                         mMonth.getSelectedItem().toString(),
                                                                                                                         mRent.getText().toString(),
                                                                                                                         mPaidEdit.getText().toString(),
                                                                                                                         mDueEdit.getText().toString());
                                }                                
                                tenantDetailDataSource.close();
                                finish();
                                startActivity(getIntent());
                                }      
                        }
                });
                
                if(isDataConnectionAvailable(this)){
                	try {
                        result = new JsonParser(UserDetailActivity.this).execute("http://maps.googleapis.com/maps/api/geocode/json?address="+ tenantModel.getAddress().replaceAll(" ", "%20") +"&sensor=false").get();        
                        latitude = new JSONObject(result).getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                        longitude = new JSONObject(result).getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                LatLng locat = new LatLng( Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(locat,15);
                mMap.animateCamera(update);
                mYear = (EditText) findViewById(R.id.year_id);	
                }
                else{
    				Toast.makeText(this, "Please turn on the Internet Connection", Toast.LENGTH_LONG).show();
                }
        }
        
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.user_detail, menu);
                return true;
        }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
                int itemId = item.getItemId();
                if (itemId == R.id.action_edit) {
                        Intent addIntent = new Intent(UserDetailActivity.this, AddNewEntry.class);
                        addIntent.putExtra("id",tenantModel.getId());
                        startActivity(addIntent);
                        return true;
                } else if (itemId == R.id.action_export_app_data) {
                        Toast.makeText(this, "Backup Created", Toast.LENGTH_SHORT).show();
                        Intent export = new Intent(UserDetailActivity.this,CsvActivity.class);
                        startActivity(export);
                        return true;
                } else if (itemId == R.id.action_about) {
                        Intent aboutIntent = new Intent(UserDetailActivity.this, AboutActivity.class);
                        startActivity(aboutIntent);
                        return true;
                } else if (itemId == R.id.action_exit) {
                        finish();
                        return true;
                } else if (itemId == android.R.id.home) {
                        finish();
                        return true;
                } else if (itemId == R.id.action_logout) {
                        Intent logoutIntent = new Intent(UserDetailActivity.this, LandlordBuddyMainActivity.class);
                        startActivity(logoutIntent);
                        session.logoutUser();
                        return true;
                } else if (itemId == R.id.action_records) {
        			Intent records = new Intent(UserDetailActivity.this,RecordsActivity.class);
        			startActivity(records);
        			return true;
        		} else {
                        return super.onOptionsItemSelected(item);
                }
        }
        @Override
        public void onRestoreInstanceState(Bundle savedInstanceState) {
          super.onRestoreInstanceState(savedInstanceState);
          // Restore UI state from the savedInstanceState.
          // This bundle has also been passed to onCreate.
          
          String myString = savedInstanceState.getString("MyString");
          th.setCurrentTabByTag(myString);
        }
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
          super.onSaveInstanceState(savedInstanceState);
          // Save UI state changes to the savedInstanceState.
          // This bundle will be passed to onCreate if the process is
          // killed and restarted.
          String Currenttab = th.getCurrentTabTag();
          savedInstanceState.putString("MyString", Currenttab);
          // etc.
        }
        @Override
        public void onClick(View view) {
                if (view == findViewById(R.id.notify_button)) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            TenantModel tenantModel = tenantDataSource.getTenant(mId);
            i.putExtra("sms_body", "Due date for the rent is nearing, Please pay your rent on time.");
            i.putExtra("address", tenantModel.getPhone());
                    i.setType("vnd.android-dir/mms-sms");
                    startActivity(i);
        }
                if (view == findViewById(R.id.delete_button)) {
                        new AlertDialog.Builder(this)
                 .setTitle("Delete Tenant ?")
                 .setMessage("Are you sure you want to delete this Tenant?")
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                         tenantDataSource.open();
                                        tenantDataSource.deleteTenant(mId);
                                        tenantDataSource.close();
                                        Intent i = new Intent(UserDetailActivity.this,HomeActivity.class);
                                        startActivity(i);
                 }
                 })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                 // do nothing
                 }
                 })
                 .show();
                        
                }
        }
        public static boolean isDataConnectionAvailable(Context context){
  	      ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
  	      NetworkInfo info = connectivityManager.getActiveNetworkInfo();
  	      if(info == null)
  	          return false;
  	      return connectivityManager.getActiveNetworkInfo().isConnected();
        }
}