package com.example.weatherApp.currentWeather;

import java.util.List;

import com.example.weatherApp.forcast.Coord;
import com.example.weatherApp.forcast.Weather;
import com.example.weatherApp.util.Sys;

public class CurrentWeather {
	public Coord coord;
	public Sys sys;
	public List<Weather> weathers;
	public String base;
	public WMain wMain;
	public Wind wind;
	public Clouds clouds;
	public int dt;
	public int id;
	public String name;
	public int cod;

	public CurrentWeather(Coord coord, Sys sys, List<Weather> weathers, String base, WMain wMain,
			Wind wind, Clouds clouds, int dt, int id, String cityName, int cod) {
		super();
		this.coord = coord;
		this.sys = sys;
		this.weathers = weathers;
		this.base = base;
		this.wMain = wMain;
		this.wind = wind;
		this.clouds = clouds;
		this.dt = dt;
		this.id = id;
		this.name = cityName;
		this.cod = cod;
	}
}
