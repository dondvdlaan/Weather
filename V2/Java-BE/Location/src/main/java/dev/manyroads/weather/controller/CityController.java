package dev.manyroads.weather.controller;

import dev.manyroads.weather.service.CityService;
import dev.manyroads.weather.model.City;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CityController {

    static Logger logger = Logger.getLogger(CityController.class.getName());

    CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /*
        Method receives location request with parameter "name" in the uri.
        Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
         */
    @GetMapping(
            value="/city",
            produces = "application/json"
    )
    public City getCityLocation(@RequestParam( value = "name") String cityName)
            throws RestClientException, Exception{

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();

        city = cityService.getCityCoordinates(cityName);

        logger.info("Returns city: " + city);

        return city;
    }
}
