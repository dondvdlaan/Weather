package dev.manyroads.weather;


import dev.manyroads.weather.model.WeatherRaw;
import dev.manyroads.weather.service.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ConditionsTest
{
    static final double LATITUDE = 12D;
    static final double LONGITUDE = 20D;
    @Mock
    WeatherService weatherService;

    @Autowired
    WebTestClient client;

    @BeforeEach
    void set() throws Exception {

        WeatherRaw weatherRaw = new WeatherRaw();
        weatherRaw.setLatitude(LATITUDE);

        Mono<WeatherRaw> mono = Mono.just(weatherRaw);

        when(weatherService.getWeatherForecast(LATITUDE, LONGITUDE))
                .thenReturn(mono);
    }
    @Test
    public void conditionControllerTest()
    {
        client
                .get()
                .uri("/conditions/" + LATITUDE + "/" + LONGITUDE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.latitude").isEqualTo(LATITUDE);
    }
}
