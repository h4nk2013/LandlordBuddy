package com.hrishikesh.landlordbuddy;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewArrayAdapter extends ArrayAdapter<TenantModel> {

	private List<TenantModel> mListModel;
	private Context mContext;
	public ListViewArrayAdapter(Context context, List<TenantModel> objects){
		super(context, R.layout.list_row, objects);
		mListModel = objects;
		mContext = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_row, parent, false);
		TenantModel tenantModel = mListModel.get(position);
		
		TextView name = (TextView) rowView.findViewById(R.id.listName);
		TextView deposit = (TextView) rowView.findViewById(R.id.listDeposit);
		TextView address = (TextView) rowView.findViewById(R.id.listAddress);
		TextView email = (TextView) rowView.findViewById(R.id.listEmail);
		TextView phone = (TextView) rowView.findViewById(R.id.listPhone);
		
		name.setText(tenantModel.getName());
		deposit.setText("Deposit: $" + tenantModel.getDeposit());
		address.setText(tenantModel.getAddress());
		email.setText(tenantModel.getEmail());
		phone.setText(tenantModel.getPhone());
		
		return rowView;
	}
}
