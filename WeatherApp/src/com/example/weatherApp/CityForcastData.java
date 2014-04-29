package com.example.weatherApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.weatherApp.forcast.CityForcast;
import com.example.weatherApp.forcast.Forcast;
import com.example.weatherApp.util.jsonReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class CityForcastData {

    private static final String TAG = "LayerData";

    private Context context;
    CityForcast mCityForcast;
    private static CityForcastData cityForcastData;

    private CityForcastData( Context context )
    {
        this.context = context.getApplicationContext();
    }

    public static CityForcastData getInstance( Context context )
    {
        if( cityForcastData == null ) cityForcastData = new CityForcastData( context );

        return cityForcastData;
    }

    public CityForcast getCityForcast()
    {
    	if (mCityForcast == null){
    	        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	        if (networkInfo != null && networkInfo.isConnected()) {
    	            new DownloadWebpageTask().execute();
    	        } else {
    	            Log.i("No network connection available.", "No network connection available.");
    	        }

    	}
        return mCityForcast;
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
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json");
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
                mCityForcast = jreader.readJsonStream(is);
                
                Log.i("MCITYFORCAST", "cod:" + mCityForcast.cod);
                Log.i("MCITYFORCAST", "message:" + mCityForcast.message);
                Log.i("MCITYFORCASTCITY", "city id:" + mCityForcast.city.id);
                Log.i("MCITYFORCASTCITY", "city name:" + mCityForcast.city.name);
                Log.i("MCITYFORCASTCITYCOORD", "city coord lon:" + mCityForcast.city.coord.lon);
                Log.i("MCITYFORCASTCITYCOORD", "city coord lat:" + mCityForcast.city.coord.lat);
                Log.i("MCITYFORCASTCITY", "city country:" + mCityForcast.city.country);
                Log.i("MCITYFORCASTCITY", "city population:" + mCityForcast.city.population);
                Log.i("MCITYFORCAST", "cnt:" + mCityForcast.cnt);
                for (int i = 0; i < mCityForcast.forcasts.size(); i++){
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": dt"+ mCityForcast.forcasts.get(i).dt);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp day"+ mCityForcast.forcasts.get(i).temp.day);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp min"+ mCityForcast.forcasts.get(i).temp.min);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp max"+ mCityForcast.forcasts.get(i).temp.max);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp night"+ mCityForcast.forcasts.get(i).temp.night);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp eve"+ mCityForcast.forcasts.get(i).temp.eve);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": temp morn"+ mCityForcast.forcasts.get(i).temp.morn);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": pressure"+ mCityForcast.forcasts.get(i).pressure);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": humidity"+ mCityForcast.forcasts.get(i).humidity);
                	for (int j = 0; j < mCityForcast.forcasts.get(i).weathers.size(); j++){
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ mCityForcast.forcasts.get(i).weathers.get(j).id);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ mCityForcast.forcasts.get(i).weathers.get(j).main);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ mCityForcast.forcasts.get(i).weathers.get(j).description);
                		Log.i("MCITYFORCASTForcast", "forcast" + i + " weather" + j + ": "  + "id "+ mCityForcast.forcasts.get(i).weathers.get(j).icon);
                	}
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": speed"+ mCityForcast.forcasts.get(i).speed);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": deg"+ mCityForcast.forcasts.get(i).deg);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": clouds"+ mCityForcast.forcasts.get(i).clouds);
                	Log.i("MCITYFORCASTForcast", "forcast" + i + ": rain"+ mCityForcast.forcasts.get(i).rain);
                }
                
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                } 
            }
        }
    }



}
