package com.example.weatherApp;

import java.util.List;

import com.example.weatherApp.forcast.Forcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ForcastListAdapter extends ArrayAdapter<Forcast>{
	public ForcastListAdapter(Context context, int textViewResourceId){
		super(context, textViewResourceId);
	}
	public ForcastListAdapter(Context context, int resource, List<Forcast> items){
		super(context, resource, items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		
		if(v == null){
			LayoutInflater vi; 
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.forcast_list_row, null);
		}
		
		Forcast p = getItem(position);
		if(p != null){
			TextView humidityView = (TextView) v.findViewById(R.id.humidityView);
			TextView tempMinView = (TextView) v.findViewById(R.id.temp_min_view);
			TextView tempMaxView = (TextView) v.findViewById(R.id.temp_min_view);
			
		
			if (humidityView != null) {
				humidityView.setText("humidity: " + p.humidity);
	        }
			if (tempMinView != null) {
				tempMinView.setText("min: " + p.temp.min);
	        }
	        if (tempMaxView != null) {
	        	tempMaxView.setText("max: " + p.temp.max);
	        }
        }
		
        return v;
	}
}
