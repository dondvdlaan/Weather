package dev.manyroads.weather.cityweather.controller;

import dev.manyroads.weather.cityweather.model.CityWeather;
import dev.manyroads.weather.cityweather.repository.CityWeatherEntity;
import dev.manyroads.weather.cityweather.repository.CityWeatherRepository;
import dev.manyroads.weather.cityweather.service.CityWeatherMapper;
import dev.manyroads.weather.cityweather.service.CityWeatherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.logging.Logger;

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

    @PostMapping(
            value = "/city",
            produces = "application/json"
    )
    public Mono<CityWeather> saveCityWeather(@RequestBody CityWeather cityWeather)
            throws RestClientException, Exception {

        logger.info("CityWeather: " + cityWeather);

        return cityWeatherService.saveCityWeather(cityWeather);
    }

    @GetMapping("/retrieve")
    public Flux<CityWeather> retrieveAllCityWeather(){

        logger.info("Retrieving all CityWeather: ");

        return cityWeatherService.retrieveCityWeather();
    }
    @GetMapping("/get/{name}")
    public Flux<CityWeather> getCityWeatherByName(@PathVariable String name){

        logger.info("Retrieving CityWeather: ");

        return cityWeatherService.getCityWeatherByName(name);
    }

    @GetMapping("/add")
    public Mono<CityWeatherEntity> adRecord(){

        logger.info("In adRecord: ");

        return cityWeatherRepository.save(new CityWeatherEntity(
                "Adam","NL",20.1D,1.6D,"GMT", new Date().toString()));
    }
    @GetMapping("/test")
    public String testController(){

        logger.info("In test: ");

        return "niksTeZein";
    }

    // --- Sub-methods ---



}
