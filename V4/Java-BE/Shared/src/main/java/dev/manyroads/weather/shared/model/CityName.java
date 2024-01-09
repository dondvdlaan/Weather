package dev.manyroads.weather.shared.model;

public class CityName {

    String cityName;

    public CityName() {
        this.cityName = "noName";
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "CityName{" +
                "cityName='" + cityName + '\'' +
                '}';
    }
}
