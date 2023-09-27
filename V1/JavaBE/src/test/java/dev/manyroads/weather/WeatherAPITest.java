package dev.manyroads.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import dev.manyroads.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherAPITest {
    WeatherService weatherService;

    @Autowired
    public WeatherAPITest(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Test
    public void testWeatherAPI() throws Exception{
        assertEquals(
                50.12,
                weatherService
                        .getWeatherForecast(50.1136, 8.6797)
                        .block()
                        .getLatitude()
        );
    }
}
