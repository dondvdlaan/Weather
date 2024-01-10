package dev.manyroads.weather.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.manyroads.weather.shared.model.City;
import dev.manyroads.weather.location.util.CityRaw2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityService2 {

    Logger logger = Logger.getLogger(CityService2.class.getName());

    String apiKey;
    String cityUrl;

    public CityService2(
            @Value("${apiKey2}") String apiKey,
            @Value("${cityUrl2}") String cityUrl) {
        this.apiKey = apiKey;
        this.cityUrl = cityUrl;
    }

    /*
    Method to obtain the latitude and logitude coordinates from API supplier based on city name
     */
    public City getCityCoordinates(String cityName)
            throws RestClientException, Exception {

         /*
        Compose uri. Example:
        https://api.geoapify.com/v1/geocode/search?text=Madrid&lang=en&limit=10&type=city&format=json&apiKey=3320d1ab41394f718f7ed3b4891e2dc9
         */
        String path = "&lang=en&limit=1&type=city&format=json&apiKey=";
        String uri = cityUrl + cityName + path + apiKey;
        logger.info("uri: " + uri);

        // Initialise variables
        ArrayList cittyArray = new ArrayList<>();
        String cityJSON = "[{}]";
        ObjectMapper JSONmapper = new ObjectMapper();
        CityRaw2 cityRaw2 = new CityRaw2();
        City city = new City();

        // Create a new synchronous RestTemplate instance
        RestTemplate rest = new RestTemplate();

        // Set header with API key
        HttpHeaders headers = new HttpHeaders();
        //headers.add("X-Api-Key", apiKey);

        // Compose httpEntity
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        try {
            // Make the HTTP GET request, marshaling the response to a String
            ResponseEntity<String> response = rest.exchange(
                    uri,
                    HttpMethod.GET,
                    httpEntity, String.class);

            logger.info("RestTemplate response: " + response);
            logger.info("RestTemplate response body: " + response.getBody());

            // Store response as JSON string
            cityJSON = response.getBody();
        } catch (RestClientException ex) {
            logger.log(Level.SEVERE, "E1 Foutje ophalen coordinaten CityService2: " + ex.getMessage());
            throw new RestClientException("E2 Foutje ophalen coordinaten CityService2. ");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "E3 Algemene fout ophalen coordinaten CityService2: " + ex.getMessage());
            throw new Exception("E4 Algemene fout ophalen coordinaten CityService2. ");
        }

        logger.info("cityJSON: " + cityJSON);
        logger.info("cityJSON length: " + cityJSON.length());

        // Convert JSON string to POJO
        try {
            cityRaw2 = JSONmapper.readValue(cityJSON, CityRaw2.class);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "E5 Alg JSON foutje: " + ex.getMessage());
            throw new Exception("E6 Alg foutje converteren JSON. ");
        }

        // Check if results is not empty
        if (cityRaw2.getResults().length > 0) {

            city.setName(cityRaw2.getResults()[0].getCity());
            city.setCountry(cityRaw2.getResults()[0].getCountry());
            city.setLatitude(cityRaw2.getResults()[0].getLat());
            city.setLongitude(cityRaw2.getResults()[0].getLon());
        }
        logger.info("citiy: " + city);

        return city;
    }

}
