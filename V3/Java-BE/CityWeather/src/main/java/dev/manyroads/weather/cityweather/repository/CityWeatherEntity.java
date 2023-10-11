package dev.manyroads.weather.cityweather.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class CityWeatherEntity {

    @Id
    String cityWeatherID;
    String name;
    String country;
    double temperature;
    double windspeed;
    String timezone;
    Date time;

    public CityWeatherEntity() {
    }

    public CityWeatherEntity(String name, String country, double temperature, double windspeed, String timezone, Date time) {
        this.name = name;
        this.country = country;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.timezone = timezone;
        this.time = time;
    }

    public String getCityWeatherID() {
        return cityWeatherID;
    }

    public void setCityWeatherID(String cityWeatherID) {
        this.cityWeatherID = cityWeatherID;
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
        return "CityWeatherEntity{" +
                "cityWeatherID=" + cityWeatherID +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", temperature=" + temperature +
                ", windspeed=" + windspeed +
                ", timezone='" + timezone + '\'' +
                ", time=" + time +
                '}';
    }
}
