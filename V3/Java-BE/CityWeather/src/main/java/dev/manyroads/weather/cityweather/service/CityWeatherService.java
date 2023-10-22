package dev.manyroads.weather.cityweather.service;

import dev.manyroads.weather.cityweather.model.CityWeather;
import dev.manyroads.weather.cityweather.repository.CityWeatherRepository;


import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<CityWeather> saveCityWeather(CityWeather cityWeather)
    {
        return cityWeatherRepository.save(cityWeatherMapper.apiToEntity(cityWeather))
                .log()
                .map(e-> cityWeatherMapper.entityToApi(e));
    }
    /*
   Method to retrieve all CityWeather from database
    */
    public Flux<CityWeather> retrieveCityWeather()
    {
        return cityWeatherRepository.findAll()
                .log()
                .map(e-> cityWeatherMapper.entityToApi(e));
    }
    /*
  Method to retrieve CityWeather by name
   */
    public Flux<CityWeather> getCityWeatherByName(String name)
    {
        return cityWeatherRepository.findByName(name)
                .log()
                .map(e-> cityWeatherMapper.entityToApi(e));
    }

}
