package dev.manyroads.weather.location.service;

import dev.manyroads.weather.location.repository.CityWeatherRepository;


import dev.manyroads.weather.shared.model.CityWeather;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityWeatherService {

    Logger logger = Logger.getLogger(CityWeatherService.class.getName());

    CityWeatherRepository cityWeatherRepository;
    CityWeatherMapper cityWeatherMapper;

    public CityWeatherService(CityWeatherRepository cityWeatherRepository, CityWeatherMapper cityWeatherMapper) {
        this.cityWeatherRepository = cityWeatherRepository;
        this.cityWeatherMapper = cityWeatherMapper;
    }

    /*
    Method to save CityWeather to database
     */
    public Mono<CityWeather> saveCityWeather(CityWeather cityWeather) {
        logger.info("In saveCityWeather: " + cityWeather);

        return cityWeatherRepository.save(cityWeatherMapper.apiToEntity(cityWeather))
                .log()
                .map(e -> cityWeatherMapper.entityToApi(e));
    }

    /**
     * Method to retrieve all CityWeather from database
     */
    public Flux<CityWeather> retrieveCityWeather() {

        return cityWeatherRepository.findAll()
                .log()
                .map(e -> cityWeatherMapper.entityToApi(e));
    }

    /**
     * Method to retrieve trend of CityWeather per city from database
     */
    public Flux<CityWeather> getCityWeatherByName(String name) {

        logger.log(Level.INFO,"***** getCityWeatherByName: " + name);

        return cityWeatherRepository.findByNameIgnoreCaseOrderByTime(name)
                .log()
                .map(e -> cityWeatherMapper.entityToApi(e));
    }

}
