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
		
		TextView name = (TextView) rowView.findViewById(R.id.name);
		TextView due = (TextView) rowView.findViewById(R.id.due);
		TextView paid = (TextView) rowView.findViewById(R.id.paid);
		TextView dueDate = (TextView) rowView.findViewById(R.id.dueDate);
		
		name.setText(tenantModel.getName());
		due.setText("Due by " + tenantModel.getPhone());
		paid.setText("Paid: " + tenantModel.getAddress());
		dueDate.setText("Due: " + tenantModel.getEmail());
		
		return rowView;
	}
}
