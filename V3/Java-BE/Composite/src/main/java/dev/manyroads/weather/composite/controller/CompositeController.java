package dev.manyroads.weather.composite.controller;

import dev.manyroads.weather.composite.scheduler.CityWeatherTrending;
import dev.manyroads.weather.composite.service.CityWeatherService;
import dev.manyroads.weather.shared.model.CityName;
import dev.manyroads.weather.shared.model.CityWeather;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

/**
 * Class CompositeController receives signals from Front End for CityWeather requests
 */
@RestController
@CrossOrigin
public class CompositeController {

    private static Logger logger = Logger.getLogger(CompositeController.class.getName());

    CityWeatherService cityWeatherService;

    public CompositeController(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    /*
    Method receives weather request for city with parameter "name" in the uri.
    Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
    return
    */
    @GetMapping(
            value = "/city",
            produces = "application/json"
    )
    public Mono<CityWeather> getCityWeather(@RequestParam(value = "name") String cityName) {

        logger.info("cityName: " + cityName);

        return cityWeatherService.getCityWeather(cityName);
    }

    /**
     * Method receives weather trend request for city with parameter "name" in the uri.
     *     Example: curl '-X' 'GET' 'http://localhost:8081/cityTrend?name=madrid'
     * @param cityName  : City name
     * @return Flux     : Stream of CityWeather trend
     */
    @GetMapping(
            value = "/cityTrend",
            produces = "application/json"
    )
    public Flux<CityWeather> getTrendCityWeather(@RequestParam(value = "name") String cityName) {

        logger.info("cityTrend cityName: " + cityName);

        return cityWeatherService.getTrendCityWeather(cityName);
    }

    // ********* Testing *********
    @PostMapping("/storeCity")
    public Mono<CityWeather> createCityWeather(@RequestBody CityWeather cityWeather) {

        logger.info("cityWeather: " + cityWeather);

        return cityWeatherService.storeCityWeather(cityWeather);
    }

    @PostMapping("/test")
    public String test1(@RequestBody String body) {
        return "Holita " + body;
    }
    // ********* END testing *********

    /**
     * Methode received City name to start trending of CityWeather
     * @param cityName  : City to be trended to DB
     * @return          : Confirmation scheduler started
     */
    @PostMapping(
            value = "/startTrend",
            consumes = "application/json")
    public ResponseEntity startTrending(@RequestBody CityName cityName) {

        logger.info("startTrending -> cityName: " + cityName.getCityName());

        // Stop trending
        CityWeatherTrending.setTrendStarted(false);
        // Communicate CityNmae and start trending to CityWeatherTrending
        CityWeatherTrending.setCityName(cityName.getCityName());
        CityWeatherTrending.setTrendStarted(true);

        return ResponseEntity.ok("Scheduler started: " + cityName);
    }

    // ---- Submethods ----

}
