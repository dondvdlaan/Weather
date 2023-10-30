package dev.manyroads.weather.composite.controller;

import dev.manyroads.weather.composite.service.CityWeatherService;
import dev.manyroads.weather.shared.model.CityWeather;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CompositeController {

    Logger logger = Logger.getLogger(CompositeController.class.getName());

    private static boolean schedulerStarted;
    CityWeatherService cityWeatherService;

    public CompositeController(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    /*
            Method receives weather request for city with parameter "name" in the uri.
            Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
             */
    @GetMapping(
            value="/city",
            produces = "application/json"
    )
    public Mono<CityWeather> getCityWeather(@RequestParam( value = "name") String cityName){

        logger.info("cityName: " + cityName);

        return cityWeatherService.getCityWeather(cityName);
    }

    /**
     *
     * @param cityName
     * @return
     */
    public Flux<CityWeather> getTrendCityWeather(@RequestParam( value = "name") String cityName){

        logger.info("cityName: " + cityName);

        // Start scheduler
        if(!schedulerStarted){

        }

        return cityWeatherService.getTrendCityWeather(cityName);
    }

    @PostMapping("/storeCity")
    public Mono<CityWeather> createCityWeather(@RequestBody CityWeather cityWeather){

        logger.info("cityWeather: " + cityWeather);

    return cityWeatherService.storeCityWeather(cityWeather);
    }

    // Testing
    @PostMapping("/test")
    public String test(@RequestBody String body)
    {
    return "Holita " + body;
    }

    @GetMapping("/getTest")
    public String test()
    {
        return "Holita ";
    }
    // END testing

    // ---- Submethods ----

}
