package dev.manyroads.weather.cityweather.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityWeatherRepository extends ReactiveMongoRepository<CityWeatherEntity, String > {

    Flux<CityWeatherEntity> findByName(String name);
}
