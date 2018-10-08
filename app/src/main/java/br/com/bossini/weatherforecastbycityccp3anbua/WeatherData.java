package br.com.bossini.weatherforecastbycityccp3anbua;

import java.util.List;

class WeatherData {
    private long dt;
    private WeatherDataDetails main;
    private List <ImageDetails> weather;

    public List<ImageDetails> getWeather() {
        return weather;
    }

    public void setWeather(List<ImageDetails> weather) {
        this.weather = weather;
    }


    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherDataDetails getMain() {
        return main;
    }

    public void setMain(WeatherDataDetails main) {
        this.main = main;
    }
}
