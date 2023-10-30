package dev.manyroads.weather.composite.service;

import dev.manyroads.weather.shared.model.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityService {

    Logger logger = Logger.getLogger(CityService.class.getName());

    String scheme;
    String cityHost;
    String cityPort;

    String cityPath;
    String cityUrl;

    public CityService(
            @Value("${scheme}") String scheme,
            @Value("${cityHost}") String cityHost,
            @Value("${cityPort}") String cityPort,
            @Value("${cityPath}") String cityPath
    ) {
        this.scheme = scheme;
        this.cityHost = cityHost;
        this.cityPort = cityPort;
        this.cityPath = cityPath;

        // Compose URL
        cityUrl = scheme + cityHost + cityPort + cityPath;
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
        }catch(RestClientException restEx){
            logger.log(Level.SEVERE,"Foutje ophalen coordinaten city: " + restEx.getMessage());
            throw new RestClientException("Foutje ophalen coordinaten city. ");
        }catch(Exception ex){
            logger.log(Level.SEVERE,"alg fout ophalen coordinaten city: " + ex.getMessage());
            throw new RestClientException("Alg fout ophalen coordinaten city. ");
        }

        logger.info("Return city: " + city);

        return city;
    }

}
