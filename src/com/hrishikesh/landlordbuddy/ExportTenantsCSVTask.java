package com.hrishikesh.landlordbuddy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportTenantsCSVTask extends AsyncTask<String, Void, Boolean>{
	private Context context;
	private TenantDataSource tenantDataSource;
 	private ProgressDialog dialog;
 	public ExportTenantsCSVTask(Context mContext) {
		// TODO Auto-generated constructor stub
    	 context = mContext;
	}
     


     // can use UI thread here
     protected void onPreExecute() {
    	dialog = new ProgressDialog(context);
 		dialog.setMessage("Loading...");
 		dialog.show();
     }
     
     
     // automatically done on worker thread (separate from UI thread)
     protected Boolean doInBackground(final String... args) {
    	
    	 
        System.out.println(context.getDatabasePath(DatabaseHelper.DATABASE_NAME.toString()));
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
           exportDir.mkdirs();
        }
        File file = new File(exportDir, "LandlordBuddyTenants_Backup.csv");

        try {
           file.createNewFile();
           tenantDataSource = new TenantDataSource(context);
           CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
           List<TenantModel> listdata = tenantDataSource.generateList();
           TenantModel tenantModel = null;
           
           String arrStr1[] ={"Name", "Address", "Phone", "Email", "Deposit"};
           csvWrite.writeNext(arrStr1);
           
           if(listdata.size() > 1)
           {
            for(int index=0; index < listdata.size(); index++)
            {
              tenantModel =listdata.get(index);
              String arrStr[] ={tenantModel.getName(), tenantModel.getAddress(), tenantModel.getAddress(), tenantModel.getEmail(), Integer.toString(tenantModel.getDeposit())};
              csvWrite.writeNext(arrStr);
            }
           }
           csvWrite.close();
//           this.copyFile(dbFile, file);
           return true;
        } catch (IOException e) {
           Log.e("mypck", e.getMessage(), e);
           return false;
        }
     }

     @Override
    protected void onProgressUpdate(Void... values) {
    	// TODO Auto-generated method stub
    	super.onProgressUpdate(values);
    }
     // can use UI thread here
     protected void onPostExecute(final Boolean success) {
    	 if (this.dialog.isShowing()) {
             this.dialog.dismiss();
          }
    	 if (success){
             Toast.makeText(context, "Export successful!", Toast.LENGTH_SHORT).show();
         }
         else {
             Toast.makeText(context, "Export failed!", Toast.LENGTH_SHORT).show();
         }
     }
    
}
