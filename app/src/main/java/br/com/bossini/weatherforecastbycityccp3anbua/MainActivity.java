package br.com.bossini.weatherforecastbycityccp3anbua;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = locationEditText.getEditableText().toString();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try{
                            URL url = createURL(cidade);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            InputStream stream = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                            String linha = null;
                            StringBuilder stringBuilder = new StringBuilder ("");
                            while ((linha = reader.readLine()) != null){
                                stringBuilder.append(linha);
                            }
                            String json = stringBuilder.toString();
                            Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();



            }
        });
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
