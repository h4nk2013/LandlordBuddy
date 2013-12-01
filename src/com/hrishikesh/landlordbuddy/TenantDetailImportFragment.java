package com.hrishikesh.landlordbuddy;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class TenantDetailImportFragment extends Fragment{
	
	private Button details;
	private Button tenants;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.tenantdetail_fragment, container, false);
		
		
		return mLinearLayout;
	}
}
