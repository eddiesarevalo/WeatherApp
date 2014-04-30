package com.example.weatherApp;

import java.text.SimpleDateFormat;
import java.util.Date;
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
			TextView dateView = (TextView) v.findViewById(R.id.date_view);
			TextView tempMinView = (TextView) v.findViewById(R.id.temp_min_view);
			TextView tempMaxView = (TextView) v.findViewById(R.id.temp_max_view);
			TextView tempMornView = (TextView) v.findViewById(R.id.temp_morn_view);
			TextView tempDayView = (TextView) v.findViewById(R.id.temp_day_view);
			TextView tempEveView = (TextView) v.findViewById(R.id.temp_eve_view);
			TextView tempNightView = (TextView) v.findViewById(R.id.temp_night_view);
			
			String dateAsText = new SimpleDateFormat("dd/MM/yyyy").format(new Date(p.dt * 1000L));
			double tempMin = convertKelvinToFahrenheit(p.temp.min);
			double tempMax = convertKelvinToFahrenheit(p.temp.max);
			double tempMorn = convertKelvinToFahrenheit(p.temp.morn);
			double tempDay = convertKelvinToFahrenheit(p.temp.day);
			double tempEve = convertKelvinToFahrenheit(p.temp.eve);
			double tempNight = convertKelvinToFahrenheit(p.temp.night);
			
			if (dateView != null) {
				dateView.setText(dateAsText);
	        }
			if (tempMinView != null) {
				tempMinView.setText("min: " + tempMin);
	        }
			if (tempMaxView != null) {
	        	tempMaxView.setText("max: " + tempMax);
	        }
	        if (tempMornView != null) {
	        	tempMornView.setText("morn: " + tempMorn);
	        }
	        if (tempDayView != null) {
	        	tempDayView.setText("day: " + tempDay);
	        }
	        if (tempEveView != null) {
	        	tempEveView.setText("eve: " + tempEve);
	        }
	        if (tempNightView != null) {
	        	tempNightView.setText("night: " + tempNight);
	        }
	        
        }
		
        return v;
	}
	private double convertKelvinToFahrenheit(double kelvinTemp){
		return 9/(5 * (kelvinTemp - 273) + 32);
	}
}
