package com.example.weatherApp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherApp.currentWeather.Clouds;
import com.example.weatherApp.currentWeather.CurrentWeather;
import com.example.weatherApp.currentWeather.WMain;
import com.example.weatherApp.currentWeather.Wind;
import com.example.weatherApp.forcast.CityForcast;
import com.example.weatherApp.forcast.Coord;
import com.example.weatherApp.forcast.Weather;

import android.util.JsonReader;

public class CurrentWeatherJsonReader {
	public CurrentWeather readJsonStream(InputStream in) throws IOException{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try{
			return readCurrentWeather(reader);
		}finally{
			reader.close();
		}
	}

	private CurrentWeather readCurrentWeather(JsonReader reader) throws IOException{
		Coord coord = null;
		Sys sys = null;
		List<Weather> weathers = null;
		String base = null;
		WMain wMain = null;
		Wind wind = null;
		Clouds clouds = null;
		int dt = -1;
		int id = -1;
		String cityName = null;
		int cod = -1;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("coord")){
				coord = readCoord(reader);
			}
			else if(name.equals("sys")){
				sys = readSys(reader);
			}
			else if(name.equals("weather")){
				weathers = readWeathers(reader);
			}
			else if(name.equals("base")){
				base = reader.nextString();
			}
			else if(name.equals("main")){
				wMain = readWMain(reader);
			}
			else if(name.equals("wind")){
				wind = readWind(reader);
			}
			else if(name.equals("clouds")){
				clouds = readClouds(reader);
			}
			else if(name.equals("dt")){
				dt = reader.nextInt();
			}
			else if(name.equals("id")){
				id = reader.nextInt();
			}
			else if(name.equals("name")){
				name = reader.nextString();
			}
			else if(name.equals("cod")){
				cod = reader.nextInt();
			}else{
				reader.skipValue();
			}
		}
				
		reader.endObject();
		return  new CurrentWeather(coord, sys, weathers, base, wMain, wind, clouds, dt, id, cityName, cod);
	}
	
	private Clouds readClouds(JsonReader reader) throws IOException{
		int all = -1;
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("all")){
				all = reader.nextInt();
			}
			else{
				reader.skipValue();
			}
		}
		
		reader.endObject();
		return new Clouds(all);
	}

	private Wind readWind(JsonReader reader) throws IOException{
		double speed = -1;
		double deg = -1000;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("speed")){
				speed = reader.nextDouble();
			}
			else if(name.equals("deg")){
				deg = reader.nextDouble();
			}
			else{
				reader.skipValue();
			}
		}
		
		reader.endObject();
		return new Wind(speed, deg);
	}

	private WMain readWMain(JsonReader reader) throws IOException{
		double temp = -1000;
		double tempMin = -1000;
		double tempMax = -1000;
		double pressure = -1000;
		double seaLevel = Double.POSITIVE_INFINITY;
		double grndLevel = Double.POSITIVE_INFINITY;
		double humidity = -1;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("temp")){
				temp = reader.nextDouble();
			}
			else if(name.equals("temp_min")){
				tempMin = reader.nextDouble();
			}
			else if(name.equals("temp_max")){
				tempMax = reader.nextDouble();
			}
			else if(name.equals("pressure")){
				pressure = reader.nextDouble();
			}
			else if(name.equals("sea_level")){
				seaLevel = reader.nextDouble();
			}
			else if(name.equals("grnd_level")){
				grndLevel = reader.nextDouble();
			}
			else if(name.equals("humidity")){
				humidity = reader.nextDouble();
			}
			else{
				reader.skipValue();
			}
		}
		
		reader.endObject();
		return new WMain(temp, tempMin, tempMax, pressure, seaLevel, grndLevel, humidity);
	}

	private List<Weather> readWeathers(JsonReader reader) throws IOException{
		List<Weather> weathers = new ArrayList<Weather>();
		
		reader.beginArray();
		while(reader.hasNext()){
			weathers.add(readWeather(reader));
		}
		reader.endArray();
		return weathers;
	}
	private Weather readWeather(JsonReader reader) throws IOException{
		int id = -1;
		String main = null;
		String description = null;
		String icon = null;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("id")){
				id = reader.nextInt();
			}
			else if(name.equals("main")){
				main = reader.nextString();
			}
			else if(name.equals("description")){
				description = reader.nextString();
			}
			else if(name.equals("icon")){
				icon = reader.nextString();
			}
		}
		
		reader.endObject();
		return new Weather(id, main, description, icon);
	}

	private Sys readSys(JsonReader reader) throws IOException{
		String message = null;
		String country = null;
		int sunrise = -1;
		int sunset = -1;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if (name.equals("message")){
				message = reader.nextString();
			}
			else if(name.equals("country")){
				reader.nextString();
			}
			else if(name.equals("sunrise")){
				reader.nextInt();
			}
			else if(name.equals("sunset")){
				reader.nextInt();
			}
			else{
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Sys(message, country, sunrise, sunset);
	}

	private Coord readCoord(JsonReader reader) throws IOException{
		double lon = -1;
		double lat = -1;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if (name.equals("lon")){
				lon = reader.nextDouble();
			}
			else if(name.equals("lat")){
				lat = reader.nextDouble();
			}
			else{
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Coord(lon, lat);
	}
}
