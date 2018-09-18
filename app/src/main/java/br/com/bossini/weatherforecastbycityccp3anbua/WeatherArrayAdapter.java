package br.com.bossini.weatherforecastbycityccp3anbua;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherArrayAdapter extends ArrayAdapter <Weather> {

    private Map <String, Bitmap> bitmaps = new HashMap<>();
    public WeatherArrayAdapter (Context context, List <Weather> forecast){
        super(context, -1, forecast);
    }


    private static class ViewHolder {
        ImageView conditionImageView;
        TextView dayTextView;
        TextView lowTextView;
        TextView hiTextView;
        TextView humidityTextView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Weather previsao = getItem(position);
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.list_item, parent, false);
        ImageView conditionImageView = raiz.findViewById(R.id.conditionImageView);
        TextView dayTextView = raiz.findViewById(R.id.dayTextView);
        TextView lowTextView = raiz.findViewById(R.id.lowTextView);
        TextView hiTextView = raiz.findViewById(R.id.hiTextView);
        TextView humidityTextView = raiz.findViewById(R.id.humidityTextView);
        dayTextView.setText(context.
                getString(R.string.day_description,
                previsao.dayOfWeek,
                previsao.description));
        lowTextView.setText(context.getString(R.string.low_temp, previsao.minTemp));
        hiTextView.setText(context.getString(R.string.high_temp, previsao.maxTemp));
        humidityTextView.setText(context.getString(R.string.humidity, previsao.humidity));

        return raiz;


        







    }













}
