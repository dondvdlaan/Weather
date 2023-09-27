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

    String apiKey;
    String cityUrl;

    public CityService(
            @Value("${apiKey}") String apiKey,
            @Value("${cityUrl}") String cityUrl) {
        this.apiKey = apiKey;
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
        ArrayList cittyArray = new ArrayList<>();
        String cityJSON = "[{\"name\": \"noName\", \"latitude\": -200D, \"longitude\": -200D, \"country\": \"noCountry\", \"population\": -1, \"is_capital\": false}]";
        ObjectMapper JSONmapper = new ObjectMapper();
        City[] cities = {new City()};

        // Create a new synchronous RestTemplate instance
        RestTemplate rest = new RestTemplate();

        // Set header with API key
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", apiKey);

        // Compose httpEntity
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        try{
            // Make the HTTP GET request, marshaling the response to a String
            ResponseEntity<String> response = rest.exchange(
                    uri,
                    HttpMethod.GET,
                    httpEntity, String.class);

            // Store response as JSON string
            cityJSON = response.getBody();
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"Foutje ophalen coordinaten city: " + ex.getMessage());
            throw new RestClientException("Foutje ophalen coordinaten city. ");
        }
        logger.info("cityJSON: " + cityJSON);

        // Convert JSON string to POJO
        if(!cityJSON.isEmpty() ) {
            try {
                cities = JSONmapper.readValue(cityJSON, City[].class);
            } catch (JsonProcessingException ex) {
                logger.log(Level.SEVERE, "JSON foutje: " + ex.getMessage());
                throw new Exception("Foutje ophalen coordinaten city. ");
            }
        }
        logger.info("cities[0]: " + cities[0]);

        return cities[0];
    }

}
