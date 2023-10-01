package dev.manyroads.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.manyroads.weather.model.City;
import dev.manyroads.weather.model.WeatherRaw;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityService {

    Logger logger = Logger.getLogger(CityService.class.getName());

    String cityUrl;

    public CityService(
            @Value("${cityUrl}") String cityUrl) {
        this.cityUrl = cityUrl;
    }

    /*
    Method to obtain the latitude and logitude coordinates from API supplier based on city name
     */
    public City getCityCoordinates(String cityName)
            throws RestClientException, Exception  {

        // Compose uri
        String uri = cityUrl + cityName;
        logger.info("uri: " + uri);

        // Initialise variables
        ArrayList cityArray = new ArrayList<>();
        String cityJSON = "[{\"name\": \"noName\", \"latitude\": -200D, \"longitude\": -200D, \"country\": \"noCountry\", \"population\": -1, \"is_capital\": false}]";
        ObjectMapper JSONmapper = new ObjectMapper();
        City city = new City();

        // Create a new synchronous RestTemplate instance
        RestTemplate rest = new RestTemplate();

        // Set header with API key
        HttpHeaders headers = new HttpHeaders();
        //headers.add("X-Api-Key", apiKey);

        // Compose httpEntity
        HttpEntity<City> httpEntity = new HttpEntity<>(headers);

        try{
            // Make the HTTP GET request, marshaling the response to a City
            ResponseEntity<City> response = rest.exchange(
                    uri,
                    HttpMethod.GET,
                    httpEntity, City.class);

            // Store response as JSON string
            city = response.getBody();
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"Foutje ophalen coordinaten city: " + ex.getMessage());
            throw new RestClientException("Foutje ophalen coordinaten city. ");
        }

        logger.info("Return city: " + city);

        return city;
    }

}
