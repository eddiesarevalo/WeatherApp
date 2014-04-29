package com.example.weatherApp.forcast;

import java.util.List;

public class CityForcast{
	public String cod;
	public String message;
	public City city;
	public int cnt;
	public List<Forcast> forcasts;
	public CityForcast(String cod, String message, City city, int cnt,
			List<Forcast> forcasts) {
		this.cod = cod;
		this.message = message;
		this.city = city;
		this.cnt = cnt;
		this.forcasts = forcasts;
	}
	
	
	
}
