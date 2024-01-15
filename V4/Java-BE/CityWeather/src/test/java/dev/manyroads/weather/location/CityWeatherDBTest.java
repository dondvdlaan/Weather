package dev.manyroads.weather.location;

import dev.manyroads.weather.location.repository.CityWeatherEntity;
import dev.manyroads.weather.location.repository.CityWeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
Starts MongoDB testcontainer MongoDbTestBase, to test with single Docker container, but without Netty server.
You can start test class in the IDE or at the bash prompt:
./gradlew :cityweather:test --tests CityWeatherTest --warning-mode=all
 */
@DataMongoTest
public class CityWeatherDBTest extends MongoDbTestBase
{
    @Autowired
    CityWeatherRepository cityWeatherRepository;

    CityWeatherEntity savedCityWeatherEntity;

    @BeforeEach
    void setup() throws Exception {

        /*
        Basic test to see if MongoDB is working
         */
        CityWeatherEntity cityWeatherEntity = new CityWeatherEntity(

                //"Berlin","GER",20.1D,1.6D,"GMT",new Date().toString()
        );

        Mono<CityWeatherEntity> mono = cityWeatherRepository.save(cityWeatherEntity);

        //assertNotNull(cityWeatherRepository.findByName("Berlin").block());
        //Assertions.assertEquals(1, (long)cityWeatherRepository.count().block());

        StepVerifier.create(mono)
                .expectNextMatches(createdEnity -> {
                    return createdEnity.getCityWeatherID() == cityWeatherEntity.getCityWeatherID() &&
                           createdEnity.getName().equals(cityWeatherEntity.getName()) ;
                })
                .verifyComplete();
    }

    @Test
    public void cityWeatherRepositoryTest() throws Exception {

        CityWeatherEntity cityWeatherEntity = new CityWeatherEntity(
                //"Paris","FR",19.1D,1.0D,"GMT",new Date().toString()
        );

        StepVerifier.create(
                cityWeatherRepository.save(cityWeatherEntity))
                .expectNextMatches(createdEntity -> {
                    savedCityWeatherEntity = createdEntity;
                return areProductEqual(cityWeatherEntity, savedCityWeatherEntity);
                })
                .verifyComplete();
    }

    private boolean areProductEqual(CityWeatherEntity cityWeatherEntity, CityWeatherEntity savedCityWeatherEntity) {

        return(
                cityWeatherEntity.getCityWeatherID().equals(savedCityWeatherEntity.getCityWeatherID()) &&
                cityWeatherEntity.getCountry().equals(savedCityWeatherEntity.getCountry()) &&
                cityWeatherEntity.getTemperature() == savedCityWeatherEntity.getTemperature() &&
                cityWeatherEntity.getWindspeed() == savedCityWeatherEntity.getWindspeed() &&
                cityWeatherEntity.getTimezone().equals(savedCityWeatherEntity.getTimezone()) &&
                cityWeatherEntity.getTime().equals(savedCityWeatherEntity.getTime())
                );
    }

    @Test
    public void saveCityWeatherToDBTest() {

    }
}
