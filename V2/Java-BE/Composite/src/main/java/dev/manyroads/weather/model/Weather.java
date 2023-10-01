package dev.manyroads.weather.model;

import java.util.Date;

/**
 * This class is an extention of model WeatherRaw
 */
public class Weather {

    public static final String STRING_DEFAULT = "<nothingToSeeHere>";
    public static final double DOUBLE_DEFAULT = -1D;

    private double temperature;
    private double windspeed;
    private String winddirection;
    private String weathercode;
    private String is_day;
    private Date time;

    public Weather() {
        this.temperature = DOUBLE_DEFAULT;
        this.windspeed = DOUBLE_DEFAULT;
        this.winddirection = STRING_DEFAULT;
        this.weathercode = STRING_DEFAULT;
        this.is_day = STRING_DEFAULT;
        this.time = new Date(0);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(String weathercode) {
        this.weathercode = weathercode;
    }

    public String getIs_day() {
        return is_day;
    }

    public void setIs_day(String is_day) {
        this.is_day = is_day;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperature='" + temperature + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", weathercode='" + weathercode + '\'' +
                ", is_day='" + is_day + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
