package dev.manyroads.weather.cityweather.controller;

import dev.manyroads.weather.cityweather.service.CityWeatherService;
import dev.manyroads.weather.model.City;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CityWeatherController {

    static Logger logger = Logger.getLogger(CityWeatherController.class.getName());
    public static boolean backupCityService = false;
    static int delayBackupCityservice = 60;

    CityWeatherService cityService;

    public CityWeatherController(CityWeatherService cityService) {
        this.cityService = cityService;
    }

    /*
            Method receives location request with parameter "name" in the uri.
            Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
             */
    @GetMapping(
            value = "/city",
            produces = "application/json"
    )
    public City getCityLocation(@RequestParam(value = "name") String cityName)
            throws RestClientException, Exception {

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();



        return city;
    }

    // --- Sub-methods ---



}
