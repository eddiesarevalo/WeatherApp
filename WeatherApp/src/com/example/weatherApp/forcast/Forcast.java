package com.example.weatherApp.forcast;

import java.util.List;

public class Forcast {
	public int dt;
	public Temp temp;
	public double pressure;
	public double humidity;
	public List<Weather> weathers;
	public double speed;
	public double deg;
	public double clouds;
	public double rain;
	public Forcast(int dt, Temp temp, double pressure, double humidity,
			List<Weather> weathers, double speed, double deg, double clouds,
			double rain) {
		this.dt = dt;
		this.temp = temp;
		this.pressure = pressure;
		this.humidity = humidity;
		this.weathers = weathers;
		this.speed = speed;
		this.deg = deg;
		this.clouds = clouds;
		this.rain = rain;
	}
	
}
