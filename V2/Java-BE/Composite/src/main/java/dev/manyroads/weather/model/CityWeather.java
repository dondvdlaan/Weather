package dev.manyroads.weather.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Model CityWeather is an interface or DTO with the Frontend, combining the data from City and WeatherRaw.
 */
public class CityWeather{

    private static final String STRING_DEFAULT = "<nothingToSeeHere>";
    private static final double DOUBLE_DEFAULT = -1D;

        String name;
        String country;
        double temperature;
        double windspeed;
        String timezone;
        Date time;

    public CityWeather() {
        this.name = STRING_DEFAULT;
        this.country = STRING_DEFAULT;
        this.temperature = DOUBLE_DEFAULT;
        this.windspeed = DOUBLE_DEFAULT;
        this.timezone = STRING_DEFAULT;
        this.time = new Date(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", temperature=" + temperature +
                ", windspeed=" + windspeed +
                ", timezone='" + timezone + '\'' +
                ", time=" + time +
                '}';
    }
}
