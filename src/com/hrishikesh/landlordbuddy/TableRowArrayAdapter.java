package com.hrishikesh.landlordbuddy;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TableRowArrayAdapter extends ArrayAdapter<TenantDetailModel> {
	private List<TenantDetailModel> mListModel;
	private Context mContext;
	public TableRowArrayAdapter(Context context, List<TenantDetailModel> objects){
		super(context, R.layout.table_row, objects);
		mListModel = objects;
		mContext = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.table_row, parent, false);
		TenantDetailModel tenantDetailModel = mListModel.get(position);
		
		TextView year = (TextView) rowView.findViewById(R.id.rowYear);
		TextView month = (TextView) rowView.findViewById(R.id.rowMonth);
		TextView paid = (TextView) rowView.findViewById(R.id.rowPaid);
		TextView rent = (TextView) rowView.findViewById(R.id.rowRent);
		TextView due = (TextView) rowView.findViewById(R.id.rowDue);
		
		year.setText(tenantDetailModel.getYear());
		month.setText(tenantDetailModel.getMonth());
		paid.setText("$" + Integer.valueOf(tenantDetailModel.getPaid()));
		rent.setText("$" + Integer.valueOf(tenantDetailModel.getRent()));
		due.setText("$" + Integer.valueOf(tenantDetailModel.getDue()));
		return rowView;
	}

}
