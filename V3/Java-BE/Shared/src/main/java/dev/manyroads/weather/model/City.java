package dev.manyroads.weather.model;

import dev.manyroads.weather.constants.ApiConstants;

/**
 * Model City is interface or DTO with the API for obtaining the latitude and longitude city coordinates
 */
public class City{

    static final String STRING_CITY_DEFAULT = ApiConstants.DEFAULT_CITY_STRING;
    static final String STRING_DEFAULT = "<nothingToSeeHere>";
    static final double DOUBLE_DEFAULT = -200D;
    static final int INTEGER_DEFAULT = -1;

    String name;
    double latitude;
    double longitude;
    String country;
    int population;
    boolean is_capital;

    public City() {
        this.name = STRING_CITY_DEFAULT;
        this.latitude = DOUBLE_DEFAULT;
        this.longitude = DOUBLE_DEFAULT;
        this.country = STRING_DEFAULT;
        this.population = INTEGER_DEFAULT;
        this.is_capital = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public boolean isIs_capital() {
        return is_capital;
    }

    public void setIs_capital(boolean is_capital) {
        this.is_capital = is_capital;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", population=" + population +
                ", is_capital=" + is_capital +
                '}';
    }
}
