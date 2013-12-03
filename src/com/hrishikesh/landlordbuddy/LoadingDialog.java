package com.hrishikesh.landlordbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadingDialog extends AsyncTask<Object, Object, Object>{
	private ProgressDialog progress;
	private Context mContext;
    public LoadingDialog(Context context) {
		// TODO Auto-generated constructor stub
    	mContext = context;
	}

	@Override
    protected void onPreExecute() 
    {
		super.onPreExecute();
        progress = new ProgressDialog(mContext);
  		progress.setMessage("Loading...");
  		progress.show();
//        progress=ProgressDialog.show(mContext, "", "Loading...");
    }

    @Override
    protected Object doInBackground(Object... params) 
    {

        // fetch your data here 

         return null;
    }

    @Override
    protected void onPostExecute(Object result) 
    {
        super.onPostExecute(result);
        progress.dismiss();
    }

}
