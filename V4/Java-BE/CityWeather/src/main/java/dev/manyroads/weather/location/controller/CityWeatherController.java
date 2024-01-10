package dev.manyroads.weather.location.controller;

import dev.manyroads.weather.location.repository.CityWeatherEntity;
import dev.manyroads.weather.location.repository.CityWeatherRepository;
import dev.manyroads.weather.location.service.CityWeatherMapper;
import dev.manyroads.weather.location.service.CityWeatherService;
import dev.manyroads.weather.shared.model.CityWeather;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Class CityWeatherController receives requests from Composite service for storage and retrieval of CityWeather
 * trend data
 */
@RestController
@CrossOrigin
public class CityWeatherController {

    static Logger logger = Logger.getLogger(CityWeatherController.class.getName());

    CityWeatherService cityWeatherService;
    CityWeatherRepository cityWeatherRepository;
    CityWeatherMapper mapper;

    public CityWeatherController(CityWeatherService cityWeatherService, CityWeatherRepository cityWeatherRepository, CityWeatherMapper mapper) {
        this.cityWeatherService = cityWeatherService;
        this.cityWeatherRepository = cityWeatherRepository;
        this.mapper = mapper;
    }

    /**
     * Method saveCityWeather stores CityWeather to DB
     *
     * @param cityWeather : CityWeather to be stored
     * @return Mono<CityWeather>    : Confirmation of stored CityWeather
     * @throws RestClientException
     * @throws Exception
     */
    @PostMapping(
            value = "/city",
            produces = "application/json"
    )
    public Mono<CityWeather> saveCityWeather(@RequestBody CityWeather cityWeather)
            throws RestClientException, Exception {

        logger.info("CityWeather: " + cityWeather);

        return cityWeatherService.saveCityWeather(cityWeather);
    }

    /**
     * Method retrieveAllCityWeather to retrieve all CityWeather stored in DB
     *
     * @return
     */
    @GetMapping("/retrieve")
    public Flux<CityWeather> retrieveAllCityWeather() {

        logger.info("Retrieving all CityWeather: ");

        return cityWeatherService.retrieveCityWeather();
    }

    /**
     * Method getCityWeatherByName to retrieve trend of CityWeather by city name
     * Example: curl '-X' 'GET' 'http://localhost:8081/getTrend/madrid'
     *
     * @param name : City name
     * @return Fluy : Flux of trend data
     */
    @GetMapping("/getTrend/{name}")
    public Flux<CityWeather> getCityWeatherByName(@PathVariable String name) {

        logger.info("Retrieving Trend CityWeather for: " + name);

        return cityWeatherService.getCityWeatherByName(name);
    }

    // ***** Testing *****
    @GetMapping("/add")
    public Mono<CityWeatherEntity> adRecord() {

        logger.info("In adRecord: ");

        return cityWeatherRepository.save(new CityWeatherEntity(
                "Adam", "NL", 20.1D, 1.6D, "GMT", new Date().toString()));
    }

    @GetMapping("/test")
    public String testController() {

        logger.info("In test: ");

        return "niksTeZein";
    }
    // ***** END Testing *****

    // --- Sub-methods ---

}
