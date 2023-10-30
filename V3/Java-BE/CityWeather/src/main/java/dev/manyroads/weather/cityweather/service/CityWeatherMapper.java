package dev.manyroads.weather.cityweather.service;

import dev.manyroads.weather.cityweather.repository.CityWeatherEntity;

import dev.manyroads.weather.shared.model.CityWeather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityWeatherMapper {

    @Mapping(target = "cityWeatherID", ignore = true)
    public CityWeatherEntity apiToEntity(CityWeather cityWeather);

    public CityWeather entityToApi(CityWeatherEntity cityWeatherEntity);
}
