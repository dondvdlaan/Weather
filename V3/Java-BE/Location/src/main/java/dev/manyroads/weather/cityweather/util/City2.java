package dev.manyroads.weather.cityweather.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.manyroads.weather.shared.constants.ApiConstants;

@JsonIgnoreProperties(ignoreUnknown=true)
public class City2 {
    String city;
    String country;
    double lon;
    double lat;

    public City2() {
        this.city       = ApiConstants.DEFAULT_CITY_STRING;
        this.country    = ApiConstants.DEFAULT_COUNTRY_STRING;
        this.lon = -200D;
        this.lat = -200D;
    }

    public City2(String city, String country, double lon, double lat) {
        this.city = city;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City2{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
