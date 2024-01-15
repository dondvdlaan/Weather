package dev.manyroads.weather.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.manyroads.weather.shared.model.City;

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

/*
Class to contact an Api to retrieve the latitude and logitude coordinates of a city
*/
@Service
public class CityService1 {

    Logger logger = Logger.getLogger(CityService1.class.getName());

    String apiKey;
    String cityUrl;

    public CityService1(
            @Value("${apiKey}") String apiKey,
            @Value("${cityUrl}") String cityUrl) {
        this.apiKey = apiKey;
        this.cityUrl = cityUrl;
    }

    /*
    Method to obtain the latitude and logitude coordinates from API supplier based on city name
     */

    public City getCityCoordinates(String cityName)
            throws RestClientException, Exception {

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

        //try{
        // Make the HTTP GET request, marshaling the response to a String
        ResponseEntity<String> response = rest.exchange(
                uri,
                HttpMethod.GET,
                httpEntity, String.class);

        logger.info("RestTemplate response: " + response);
        logger.info("RestTemplate response body: " + response.getBody());

        // Store response as JSON string
        cityJSON = response.getBody();

            /*
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"E1 Foutje ophalen coordinaten city: " + ex.getMessage());
            throw new RestClientException("E2 Foutje ophalen coordinaten city. ");
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "E3 Algemene fout ophalen coordinaten city: " + ex.getMessage());
            throw new Exception("E4 Algemene fout ophalen coordinaten city. ");
        }
             */

        logger.info("cityJSON: " + cityJSON);
        logger.info("cityJSON length: " + cityJSON.length());

        // Check if reply of API is not empty. If response is "[]", then no correspondent city was found
        if (cityJSON.length() > 2) {
            // Convert JSON string to POJO
            try {
                cities = JSONmapper.readValue(cityJSON, City[].class);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "E5 Alg JSON foutje: " + ex.getMessage());
                throw new Exception("E6 Alg foutje converteren JSON. ");
            }
        }

        logger.info("cities[0]: " + cities[0]);

        return cities[0];
    }

}
