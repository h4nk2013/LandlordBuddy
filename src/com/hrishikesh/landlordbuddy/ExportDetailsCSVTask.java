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

public class ExportDetailsCSVTask extends AsyncTask<String, Void, Boolean> {
	private Context context;
	private TenantDetailDataSource tenantDetailDataSource;
 	private ProgressDialog dialog;
 	public ExportDetailsCSVTask(Context mContext) {
		// TODO Auto-generated constructor stub
    	 context = mContext;
	}
     


     // can use UI thread here
     protected void onPreExecute() {
    	dialog = new ProgressDialog(context);
 		dialog.setMessage("Exporting Details table to CSV...");
 		dialog.show();
     }
     
     
     // automatically done on worker thread (separate from UI thread)
     protected Boolean doInBackground(final String... args) {
    	
    	 
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
           exportDir.mkdirs();
        }
        File file = new File(exportDir, "LandlordBuddyDetails_Backup.csv");

        try {
           file.createNewFile();
           tenantDetailDataSource = new TenantDetailDataSource(context);
           CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
           List<TenantDetailModel> listdata = tenantDetailDataSource.generateList();
           TenantDetailModel tenantDetailModel = null;
           
           String arrStr1[] ={"Year", "Month", "Rent", "Paid", "Due"};
           csvWrite.writeNext(arrStr1);
           
           if(listdata.size() > 1)
           {
            for(int index=0; index < listdata.size(); index++)
            {
            	tenantDetailModel =listdata.get(index);
              String arrStr[] ={tenantDetailModel.getYear(),tenantDetailModel.getMonth(), Integer.toString(tenantDetailModel.getRent()), Integer.toString(tenantDetailModel.getPaid()), Integer.toString(tenantDetailModel.getDue())};
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
