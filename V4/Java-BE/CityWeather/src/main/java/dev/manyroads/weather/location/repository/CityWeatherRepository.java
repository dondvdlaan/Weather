package dev.manyroads.weather.location.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CityWeatherRepository extends ReactiveMongoRepository<CityWeatherEntity, String > {

    Flux<CityWeatherEntity> findByNameIgnoreCase(String name);
    Flux<CityWeatherEntity> findByNameIgnoreCaseOrderByTime(String name);
}
