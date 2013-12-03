package com.hrishikesh.landlordbuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExportDatabaseFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		new ExportSQLiteDatabase((CsvActivity)(getActivity())).execute();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
