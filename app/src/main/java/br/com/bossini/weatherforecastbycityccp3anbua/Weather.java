package br.com.bossini.weatherforecastbycityccp3anbua;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Weather {
    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconName;

    public Weather (long timeStamp, double minTemp, double maxTemp,
                    double humidity, String description, String iconName){

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        this.dayOfWeek = convertTimeStampToDay (timeStamp);
        this.minTemp = numberFormat.format(minTemp) + "\u00B0C";
        this.maxTemp = numberFormat.format(maxTemp) + "\u00B0C";
        this.humidity = NumberFormat.
                getPercentInstance().
                format(humidity / 100.0);
        this.description = description;
        this.iconName = iconName;
    }

    public Weather(WeatherData data) {
        this.dayOfWeek = convertTimeStampToDay(data.getDt());
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        this.minTemp = numberFormat.format(data.getMain().getTemp_min()) + "\u00B0C";
        this.maxTemp = numberFormat.format(data.getMain().getTemp_max()) + "\u00B0C";
        this.humidity = NumberFormat.getPercentInstance().format(data.getMain().getHumidity() / 100.);
        this.description = data.getWeather().get(0).getDescription();
        this.iconName = data.getWeather().get(0).getIcon();
    }

    private static String convertTimeStampToDay (long timeStamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( timeStamp * 1000);
        TimeZone timeZone = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND,
                timeZone.getOffset(calendar.getTimeInMillis()));
        return
                new SimpleDateFormat("EEEE").format(calendar.getTime());
    }
}













