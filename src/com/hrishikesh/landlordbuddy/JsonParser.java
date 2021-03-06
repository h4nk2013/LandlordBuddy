package com.hrishikesh.landlordbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class JsonParser extends AsyncTask<String, Void, String> {
	
	private Context mContext;
	public JsonParser(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	public Context context;
	public ProgressDialog dialog;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
        dialog = new ProgressDialog(mContext);
  		dialog.setMessage("Loading...");
  		dialog.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String...params) {
		HttpURLConnection con;
		 InputStream is;
		 String newUrl = params[0];
			try {
				con = (HttpURLConnection) ( new URL(newUrl)).openConnection();
				con.setRequestMethod("POST");
			    con.setDoInput(true);
			    con.setDoOutput(true);
			    con.connect();
			    StringBuffer buffer = new StringBuffer();
		        is = con.getInputStream();
		        BufferedReader br = new BufferedReader(new InputStreamReader(is));
		        String line = null;
		        while (  (line = br.readLine()) != null )
		            buffer.append(line + "\r\n");		        
		        is.close();
		        con.disconnect();		        		        
		        return buffer.toString();			    		        
			} 
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	};
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		super.onPostExecute(result);
	}



}
