package com.hrishikesh.landlordbuddy;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TenantImportFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.tenant_fragment, container, false);
		return mLinearLayout;
	}

}
