package com.hrishikesh.landlordbuddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ExportSQLiteDatabase extends AsyncTask<String, Void, Boolean> {
	private Context context;
 	private ProgressDialog dialog;
 	public ExportSQLiteDatabase(Context mContext) {
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

        File dbFile = new File(context.getDatabasePath(DatabaseHelper.DATABASE_NAME).toString());
        System.out.println(context.getDatabasePath(DatabaseHelper.DATABASE_NAME.toString()));
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
           exportDir.mkdirs();
        }
        File file = new File(exportDir, "LandlordBuddy_DB");

        try {
           file.createNewFile();
           this.copyFile(dbFile, file);
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
     void copyFile(File src, File dst) throws IOException {
         FileChannel inChannel = new FileInputStream(src).getChannel();
         FileChannel outChannel = new FileOutputStream(dst).getChannel();
         try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
         } finally {
            if (inChannel != null)
               inChannel.close();
            if (outChannel != null)
               outChannel.close();
         }
      }
	
	
}
