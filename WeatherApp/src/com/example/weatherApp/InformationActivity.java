package com.example.weatherApp;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.weatherApp.currentWeather.CurrentWeather;
import com.example.weatherApp.forcast.CityForcast;
import com.example.weatherApp.util.CurrentWeatherJsonReader;
import com.example.weatherApp.util.jsonReader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class InformationActivity extends Activity implements GestureDetector.OnGestureListener{
	private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_MAX_OFF_PATH = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final String DEBUG_TAG = "Gestures";
	//currentWeatherURL = "http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139";
	//weatherForcastURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
	private GestureDetectorCompat mDetector;
	TextView cityNameView;
	CityForcast cityForcast;
	CurrentWeather currentWeather;
	ListView forcastListView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		
		mDetector = new GestureDetectorCompat(this, this);
		cityNameView = (TextView)findViewById(R.id.city_name);
		forcastListView = (ListView) findViewById(R.id.forcast_list);

        ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute();
        } else {
            Log.i("No network connection available.", "No network connection available.");
        }


       
        
		
	}
	public boolean onTouchEvent(MotionEvent event){
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	Log.i(DEBUG_TAG, "rightToLeft");
            	finish();
            	overridePendingTransition( R.anim.anim_slide_in_left, R.anim.anim_slide_out_left );
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	Log.i(DEBUG_TAG, "leftToRight");
            	finish();
            	overridePendingTransition( R.anim.anim_slide_in_right, R.anim.anim_slide_out_right );
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	private class DownloadWebpageTask extends AsyncTask<Void, Void, Void> {
        @Override
		protected Void doInBackground(Void... params) {
        	try {
                downloadUrl();
           } catch (IOException e) {
               e.printStackTrace();
           }
        	return null;
		}
       
        private void downloadUrl() throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
                
            try {
            	String weatherForcastURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
                URL url = new URL(weatherForcastURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DEBUG_TAG", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
               /* String contentAsString = readIt(is, len);*/
                //return contentAsString;
                jsonReader jreader = new jsonReader();
                cityForcast = jreader.readJsonStream(is);
                
               /* Log.i("MCITYFORCAST", "cod:" + cityForcast.cod);
                Log.i("MCITYFORCAST", "message:" + cityForcast.message);
                Log.i("MCITYFORCASTCITY", "city id:" + cityForcast.city.id);
                Log.i("MCITYFORCASTCITY", "city name:" + cityForcast.city.name);
                Log.i("MCITYFORCASTCITYCOORD", "city coord lon:" + cityForcast.city.coord.lon);
                Log.i("MCITYFORCASTCITYCOORD", "city coord lat:" + cityForcast.city.coord.lat);
                Log.i("MCITYFORCASTCITY", "city country:" + cityForcast.city.country);
                Log.i("MCITYFORCASTCITY", "city population:" + cityForcast.city.population);
                Log.i("MCITYFORCAST", "cnt:" + cityForcast.cnt);
                for (int i = 0; i < cityForcast.forcasts.size(); i++){
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": dt"+ cityForcast.forcasts.get(i).dt);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp day"+ cityForcast.forcasts.get(i).temp.day);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp min"+ cityForcast.forcasts.get(i).temp.min);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp max"+ cityForcast.forcasts.get(i).temp.max);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp night"+ cityForcast.forcasts.get(i).temp.night);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp eve"+ cityForcast.forcasts.get(i).temp.eve);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp morn"+ cityForcast.forcasts.get(i).temp.morn);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": pressure"+ cityForcast.forcasts.get(i).pressure);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": humidity"+ cityForcast.forcasts.get(i).humidity);
                	for (int j = 0; j < cityForcast.forcasts.get(i).weathers.size(); j++){
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ cityForcast.forcasts.get(i).weathers.get(j).id);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ cityForcast.forcasts.get(i).weathers.get(j).main);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ cityForcast.forcasts.get(i).weathers.get(j).description);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ cityForcast.forcasts.get(i).weathers.get(j).icon);
                	}
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": speed"+ cityForcast.forcasts.get(i).speed);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": deg"+ cityForcast.forcasts.get(i).deg);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": clouds"+ cityForcast.forcasts.get(i).clouds);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": rain"+ cityForcast.forcasts.get(i).rain);
                }
                */
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                } 
            }
            
            try {
            	String currentWeatherURL = "http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139";
                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json");
            	URL url = new URL(currentWeatherURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DEBUG_TAG", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
               /* String contentAsString = readIt(is, len);*/
                //return contentAsString;
                
               CurrentWeatherJsonReader currentWeatherJsonReader = new CurrentWeatherJsonReader();
                currentWeather = currentWeatherJsonReader.readJsonStream(is);
                
                /*Log.i("CURRENTWEATHER", "currentWeather/coord/lon:" + currentWeather.coord.lon);
                Log.i("CURRENTWEATHER", "currentWeather/coord/lat:" + currentWeather.coord.lat);
                Log.i("CURRENTWEATHER", "currentWeather/sys/message:" + currentWeather.sys.message);
                Log.i("CURRENTWEATHER", "currentWeather/sys/country:" + currentWeather.sys.country);
                Log.i("CURRENTWEATHER", "currentWeather/sys/sunrise:" + currentWeather.sys.sunrise);
                Log.i("CURRENTWEATHER", "currentWeather/sys/sunset:" + currentWeather.sys.sunset);
                Log.i("CURRENTWEATHER", "currentWeather/weather/id:" + currentWeather.weathers.get(0).id);
                Log.i("CURRENTWEATHER", "currentWeather/weather/main:" + currentWeather.weathers.get(0).main);
                Log.i("CURRENTWEATHER", "currentWeather/weather/description:" + currentWeather.weathers.get(0).description);
                Log.i("CURRENTWEATHER", "currentWeather/weather/icon:" + currentWeather.weathers.get(0).icon);
                Log.i("CURRENTWEATHER", "currentWeather/base:" + currentWeather.base);
                Log.i("CURRENTWEATHER", "currentWeather/main/temp:" + currentWeather.wMain.temp);
                Log.i("CURRENTWEATHER", "currentWeather/main/temp_min:" + currentWeather.wMain.tempMin);
                Log.i("CURRENTWEATHER", "currentWeather/main/temp_max:" + currentWeather.wMain.tempMax);
                Log.i("CURRENTWEATHER", "currentWeather/main/pressure:" + currentWeather.wMain.pressure);
                Log.i("CURRENTWEATHER", "currentWeather/main/seaLevel:" + currentWeather.wMain.seaLevel);
                Log.i("CURRENTWEATHER", "currentWeather/main/grndLevel:" + currentWeather.wMain.grndLevel);
                Log.i("CURRENTWEATHER", "currentWeather/main/humidity:" + currentWeather.wMain.humidity);
                Log.i("CURRENTWEATHER", "currentWeather/wind/speed:" + currentWeather.wind.speed);
                Log.i("CURRENTWEATHER", "currentWeather/wind/deg:" + currentWeather.wind.deg);
                Log.i("CURRENTWEATHER", "currentWeather/clouds/all:" + currentWeather.clouds.all);
                Log.i("CURRENTWEATHER", "currentWeather/dt:" + currentWeather.dt);
                Log.i("CURRENTWEATHER", "currentWeather/id:" + currentWeather.id);
                Log.i("CURRENTWEATHER", "currentWeather/name:" + currentWeather.name);
                Log.i("CURRENTWEATHER", "currentWeather/cod:" + currentWeather.cod);*/
                
                
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                } 
            }
        }
        
        @Override
        protected void onPostExecute(Void result) {
        	cityNameView.setText("Country: " + cityForcast.city.country + " city:" + cityForcast.city.name);
        	ForcastListAdapter customAdapter = new ForcastListAdapter(getApplicationContext(), R.layout.forcast_list_row, cityForcast.forcasts);
        	forcastListView .setAdapter(customAdapter);
        }
    }


}

