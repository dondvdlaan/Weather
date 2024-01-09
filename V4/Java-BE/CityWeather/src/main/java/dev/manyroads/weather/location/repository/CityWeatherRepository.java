package dev.manyroads.weather.location.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CityWeatherRepository extends ReactiveMongoRepository<CityWeatherEntity, String > {

    Flux<CityWeatherEntity> findByName(String name);
    Flux<CityWeatherEntity> findByNameOrderByTime(String name);
}
