package br.com.bossini.weatherforecastbycityccp3anbua;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private ListView weatherListView;
    private WeatherArrayAdapter adapter;
    private List <Weather> weatherList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherListView = findViewById(R.id.weatherListView);
        weatherList = new ArrayList<>();
        adapter = new WeatherArrayAdapter(this, weatherList);
        weatherListView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = locationEditText.getEditableText().toString();
                /*new Thread(new Runnable(){
                    @Override
                    public void run() {
                }).start();*/
                WebServiceClient client = new WebServiceClient();
                client.execute(cidade);



            }
        });
    }



    private class WebServiceClient extends AsyncTask <String, Void, String>{
        @Override
        protected String doInBackground(String... cidade) {
            try{
                URL url = createURL(cidade[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String linha = null;
                StringBuilder stringBuilder = new StringBuilder ("");
                while ((linha = reader.readLine()) != null){
                    stringBuilder.append(linha);
                }
                return stringBuilder.toString();
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String json) {
            //Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
            try {
                weatherList.clear();
                Gson gson = new Gson();
                ForecastData previsoes = gson.fromJson(json, ForecastData.class);
                for (WeatherData data : previsoes.getList()) {
                    weatherList.add(new Weather(data));
                }
                /*JSONObject previsoes = new JSONObject(json);
                JSONArray list = previsoes.getJSONArray("list");
                for (int i = 0; i < list.length(); i++){
                    JSONObject previsao = list.getJSONObject(i);
                    long dt = previsao.getLong("dt");
                    JSONObject main = previsao.getJSONObject("main");
                    double temp_min = main.getDouble("temp_min");
                    double temp_max = main.getDouble("temp_max");
                    int humidity = main.getInt ("humidity");
                    String description = previsao.getJSONArray("weather").getJSONObject(0).
                            getString("description");
                    String icon =  previsao.getJSONArray("weather").getJSONObject(0).
                            getString("icon");
                    Weather weather = new Weather(dt,
                            temp_min, temp_max, humidity,
                            description, icon);
                    weatherList.add(weather);
                }*/
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private URL createURL (String cidade){
        try{
            String apiKey = getString(R.string.api_key);
            String baseURL = getString(R.string.web_service_url);
            String urlString = baseURL + URLEncoder.encode(cidade, "UTF-8");
            urlString += "&units=metric&APPID=" + apiKey;
            return new URL(urlString);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
