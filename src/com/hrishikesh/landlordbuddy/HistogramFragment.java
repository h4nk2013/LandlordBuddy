package com.hrishikesh.landlordbuddy;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HistogramFragment extends Fragment {
	private View view;
	private TenantDetailDataSource tenantDetailDataSource;
	private int[] due = new int[12];
	private int[] paid = new int[12];
	private String[] mMonth = new String[] {
		        "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
		        "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		tenantDetailDataSource = new TenantDetailDataSource(getActivity());
		List<TenantDetailModel> data = tenantDetailDataSource.generateList();
		 for(TenantDetailModel t : data){
			 if(t.getMonth().equals("January")){
				 due[0] = due[0] + t.getDue();
				 paid[0] = paid[0] + t.getPaid();
			 } else if(t.getMonth().equals("February")){
				 due[1] = due[1] + t.getDue();
				 paid[1] = paid[1] + t.getPaid();
			 } else if(t.getMonth().equals("March")){
				 due[2] = due[2] + t.getDue();
				 paid[2] = paid[2] + t.getPaid();
			 } else if(t.getMonth().equals("April")){
				 due[3] = due[3] + t.getDue();
				 paid[3] = paid[3] + t.getPaid();				 
			 } else if(t.getMonth().equals("May")){
				 due[4] = due[4] + t.getDue();
				 paid[4] = paid[4] + t.getPaid();				 
			 } else if(t.getMonth().equals("June")){
				 due[5] = due[5] + t.getDue();
				 paid[5] = paid[5] + t.getPaid();				 
			 } else if(t.getMonth().equals("July")){
				 due[6] = due[6] + t.getDue();
				 paid[6] = paid[6] + t.getPaid();				 
			 } else if(t.getMonth().equals("August")){
				 due[7] = due[7] + t.getDue();
				 paid[7] = paid[7] + t.getPaid();				 
			 } else if(t.getMonth().equals("September")){
				 due[8] = due[8] + t.getDue();
				 paid[8] = paid[8] + t.getPaid();
			 } else if(t.getMonth().equals("October")){
				 due[9] = due[9] + t.getDue();
				 paid[9] = paid[9] + t.getPaid();				 
			 } else if(t.getMonth().equals("November")){
				 due[10] = due[10] + t.getDue();
				 paid[10] = paid[10] + t.getPaid();				 
			 } else {
				 due[11] = due[11] + t.getDue();
				 paid[11] = paid[11] + t.getPaid();				 
			 }
		 }
		LinearLayout mLayout = (LinearLayout) inflater.inflate(R.layout.histogram_fragment, container, false);
		openChart();
		mLayout.addView(view);
		return mLayout;
	}
	
    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11 };
 
        // Creating an  XYSeries for Income
        XYSeries dueSeries = new XYSeries("Rent Due");
        // Creating an  XYSeries for Expense
        XYSeries paidSeries = new XYSeries("Rent Paid");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            dueSeries.add(i,due[i]);
            paidSeries.add(i,paid[i]);
        }
 
        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(dueSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(paidSeries);
 
        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer dueRenderer = new XYSeriesRenderer();
        dueRenderer.setColor(Color.rgb(130, 130, 230));
        dueRenderer.setFillPoints(true);
        dueRenderer.setLineWidth(2);
        dueRenderer.setDisplayChartValues(true);
 
        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer paidRenderer = new XYSeriesRenderer();
        paidRenderer.setColor(Color.rgb(220, 80, 80));
        paidRenderer.setFillPoints(true);
        paidRenderer.setLineWidth(2);
        paidRenderer.setDisplayChartValues(true);
 
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Rent Due vs Rent Paid Chart");
        multiRenderer.setXTitle("All Years");
        multiRenderer.setYTitle("Amount in Dollars");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }
 
        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(dueRenderer);
        multiRenderer.addSeriesRenderer(paidRenderer);
 
        view = ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer, null);
 
 
    }
}
