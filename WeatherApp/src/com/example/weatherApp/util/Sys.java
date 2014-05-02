package com.example.weatherApp.util;

public class Sys {
	public String message;
	public String country;
	public int sunrise;
	public int sunset;
	
	public Sys(String message, String country, int sunrise, int sunset) {
		this.message = message;
		this.country = country;
		this.sunrise = sunrise;
		this.sunset = sunset;
	}
	
}
