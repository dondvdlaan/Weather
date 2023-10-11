package dev.manyroads.weather.cityweather.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CityWeatherRepository extends ReactiveCrudRepository<CityWeatherEntity, String > {
}
