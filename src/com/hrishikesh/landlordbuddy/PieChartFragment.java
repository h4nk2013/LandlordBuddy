package com.hrishikesh.landlordbuddy;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PieChartFragment extends Fragment {
	
	private LinearLayout mLinearLayout;
	private TenantDetailDataSource tenantDetailDataSource;
	private int totalDue = 0, totalPaid = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.piechart_fragment, container, false);
		tenantDetailDataSource = new TenantDetailDataSource(getActivity());
		createPieChart();
		return mLinearLayout;
	}
	 private void createPieChart() {

		 List<TenantDetailModel> data = tenantDetailDataSource.generateList();
		 for(TenantDetailModel t : data){
			 totalDue = totalDue + t.getDue();
			 totalPaid = totalPaid + t.getPaid();
		 }
		// Pie Chart Section Names
		String[] code = new String[] { "Rent Due of $" + totalDue, "Rent Paid $" + totalPaid };

		// Pie Chart Section Value
		double[] distribution = { totalDue, totalPaid };

		// Color of each Pie Chart Sections
		int[] colors = { Color.RED, Color.GREEN };

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries("Rent Distribution");
		for (int i = 0; i < distribution.length; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(code[i], distribution[i]);
		}
		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for (int i = 0; i < distribution.length; i++) {
			SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
			seriesRenderer.setColor(colors[i]);
			seriesRenderer.setDisplayChartValues(true);
			// Adding a renderer for a slice
			defaultRenderer.addSeriesRenderer(seriesRenderer);
		}

		defaultRenderer.setLegendTextSize(50);
		defaultRenderer.setLabelsTextSize(30);
		defaultRenderer.setLabelsColor(Color.BLACK);
		defaultRenderer.setChartTitle("Rent Distribution $" + (totalDue+totalPaid));
		defaultRenderer.setChartTitleTextSize(50);
		defaultRenderer.setZoomButtonsVisible(true);
		defaultRenderer.setBackgroundColor(45454545);

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		
		View view = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
		mLinearLayout.addView(view);

		}

}
