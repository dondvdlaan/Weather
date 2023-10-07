package dev.manyroads.weather.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CityRaw2 {
City2[] results;



    public CityRaw2() {
        this.results = new City2[]{new City2()};
    }

    public CityRaw2(City2[] results) {
        this.results = results;
    }

    public City2[] getResults() {
        return results;
    }


    public void setResults(City2[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CityRaw{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
