package dev.manyroads.weather.cityweather;

import dev.manyroads.weather.cityweather.model.CityWeather;
import dev.manyroads.weather.cityweather.service.CityWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.just;

import java.util.Date;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CityWeatherControllerTest {

    @MockBean
    CityWeatherService cityWeatherService;

    @Autowired
    WebTestClient client;


    @Test
    void saveCityWeatherTest() {

        CityWeather cityWeather =
                new CityWeather("Adam", "NL", 15d, 1.5d, "GNT", new Date().toString());

        Mono<CityWeather> mono = Mono.just(cityWeather);

        // Mocking
        when(cityWeatherService.saveCityWeather(cityWeather))
                .thenReturn(mono);

        client
                .post()
                .uri("/city")
                .body(mono, CityWeather.class)
                .exchange()
                .expectBody(CityWeather.class)
                .consumeWith(m->m.getResponseBody().getName().equals("Adam"));
    }

}
