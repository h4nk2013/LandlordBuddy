package com.hrishikesh.landlordbuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExportOptionFragment extends Fragment{

	private Button details;
	private Button tenants;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.export_option_fragment, container, false);
		
		details = (Button) mLinearLayout.findViewById(R.id.detailsExport);
		tenants = (Button) mLinearLayout.findViewById(R.id.tenantExport);
		
		details.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((CsvActivity)(getActivity())).importDetails();
			}
		});
		
		tenants.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((CsvActivity)(getActivity())).importTenant();
			}
		});
		return mLinearLayout;
	}
}
