package com.example.weatherApp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherApp.forcast.City;
import com.example.weatherApp.forcast.CityForcast;
import com.example.weatherApp.forcast.Coord;
import com.example.weatherApp.forcast.Forcast;
import com.example.weatherApp.forcast.Temp;
import com.example.weatherApp.forcast.Weather;

import android.util.JsonReader;

public class jsonReader {
	public CityForcast readJsonStream(InputStream in) throws IOException{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try{
			return readCityForcast(reader);
			
		}finally{
			reader.close();
		}
	}


	private CityForcast  readCityForcast(JsonReader reader) throws IOException{
		String cod = null;
		String message = null;
		City city = null;
		int cnt = -1;
		List<Forcast> forcasts = null;
		
		reader.beginObject();
		while (reader.hasNext()){
			String name = reader.nextName();
			if (name.equals("cod")){
				cod = reader.nextString();
			}
			else if(name.equals("message")){
				message = reader.nextString();
			}
			else if(name.equals("city")){
				city = readCity(reader);
			}
			else if(name.equals("cnt")){
				cnt = reader.nextInt();
			}
			else if(name.equals("list")){
				forcasts = readForcasts(reader);
			}
			else{
				reader.skipValue();
			}
		}
		reader.endObject();
		return  new CityForcast(cod, message, city, cnt,
		forcasts);
		
	}


	private City readCity(JsonReader reader) throws IOException{
		int id = -1;
		String name = null;
		Coord coord = null;
		String country = null;
		int population = -1;
		
		reader.beginObject();
		while(reader.hasNext()){
			String readName = reader.nextName();
			if (readName.equals("id")){
				id = reader.nextInt();
			}
			else if (readName.equals("name")){
				name = reader.nextString();
			}
			else if (readName.equals("coord")){
				coord = readCoord(reader);
			}
			else if (readName.equals("country")){
				country = reader.nextString();
			}
			else if (readName.equals("population")){
				population = reader.nextInt();
			}
			else{
				reader.skipValue();
			}
		}
		reader.endObject();
		return new City(id, name, coord, country, population);
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
	
	private List<Forcast> readForcasts(JsonReader reader) throws IOException{
		List<Forcast> weathers = new ArrayList<Forcast>();
		reader.beginArray();
		while(reader.hasNext()){
			weathers.add(readForcast(reader));
		}
		reader.endArray();
		return weathers;
	}


	private Forcast readForcast(JsonReader reader) throws IOException{
		int dt = -1;
		Temp temp = null;
		double pressure = -1;
		double humidity = -1;
		List<Weather> weathers = null;
		double speed = -1000;
		double deg = -1000;
		double clouds = -1000;
		double rain = -1000;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if (name.equals("dt")){
				dt = reader.nextInt();
			}
			else if(name.equals("temp")){
				temp = readTemp(reader);
			}
			else if(name.equals("pressure")){
				pressure = reader.nextDouble();
			}
			else if(name.equals("humidity")){
				humidity = reader.nextDouble();
			}
			else if(name.equals("weather")){
				weathers = readWeathers(reader);
			}
			else if(name.equals("speed")){
				speed = reader.nextDouble();
			}
			else if(name.equals("deg")){
				deg = reader.nextDouble();
			}
			else if(name.equals("clouds")){
				clouds = reader.nextDouble();
			}
			else if(name.equals("rain")){
				rain = reader.nextDouble();
			}
			else{
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Forcast(dt, temp, pressure, humidity, weathers, speed, deg, clouds, rain);
	}


	private Temp readTemp(JsonReader reader) throws IOException{
		double day = -10000;
		double min = -10000;
		double max = -10000;
		double night = -10000;
		double eve = -10000;
		double morn = -10000;
		
		reader.beginObject();
		while(reader.hasNext()){
			String name = reader.nextName();
			if(name.equals("day")){
				day = reader.nextDouble();
			}
			else if(name.equals("min")){
				min = reader.nextDouble();
			}
			else if(name.equals("max")){
				max = reader.nextDouble();
			}
			else if(name.equals("night")){
				night = reader.nextDouble();
			}
			else if(name.equals("eve")){
				eve = reader.nextDouble();
			}
			else if(name.equals("morn")){
				morn = reader.nextDouble();
			}
		}
		reader.endObject();
		return new Temp(day, min, max, night, eve, morn);
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
}
