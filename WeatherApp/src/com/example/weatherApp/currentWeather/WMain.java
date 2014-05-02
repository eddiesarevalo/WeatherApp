package com.example.weatherApp.currentWeather;

public class WMain {
	public double temp;
	public double tempMin;
	public double tempMax;
	public double pressure;
	public double seaLevel;
	public double grndLevel;
	public double humidity;
	public WMain(double temp, double tempMin, double tempMax, double pressure,
			double seaLevel, double grndLevel, double humidity) {
		super();
		this.temp = temp;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.pressure = pressure;
		this.seaLevel = seaLevel;
		this.grndLevel = grndLevel;
		this.humidity = humidity;
	}
	
}
