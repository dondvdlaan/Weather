package dev.manyroads.weather.cityweather;


import dev.manyroads.weather.cityweather.repository.CityWeatherEntity;
import dev.manyroads.weather.cityweather.repository.CityWeatherRepository;
import dev.manyroads.weather.model.City;
import dev.manyroads.weather.cityweather.service.CityWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/*
Creates a web application context (reactive or servlet based) and sets a
server.port=0 Environment property (which usually triggers listening on a random port).
 */
//@SpringBootTest(webEnvironment = RANDOM_PORT)

// Starts MongoDB testcontainer MongoDbTestBase, to test with single Docker container, but without Netty server
@DataMongoTest
public class CityWeatherTest extends MongoDbTestBase
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
                "Berlin","GER",20.1D,1.6D,"GMT",new Date()
        );

        StepVerifier.create(cityWeatherRepository.save(cityWeatherEntity))
                .expectNextMatches(createdEnity -> {
                    return createdEnity.getCityWeatherID() == cityWeatherEntity.getCityWeatherID();
                })
                .verifyComplete();
    }

    @Test
    public void cityWeatherRepositoryTest() throws Exception {

        CityWeatherEntity cityWeatherEntity = new CityWeatherEntity(
                "Paris","FR",19.1D,1.0D,"GMT",new Date()
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


/*
        client
                .get()
                .uri("/city?name=" + CITY)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$.name").isEqualTo(CITY);
    }

    @Test
    public void cityControllerInternalServerErrorTest() throws Exception{

        client
                .get()
                .uri("/city?name=" + NONE_EXISTING_CITY)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/city");
    }

    */
}
