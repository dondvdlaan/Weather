package dev.manyroads.weather;

import dev.manyroads.weather.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CityTest {

    CityService cityService;

    @Autowired
    public CityTest(CityService cityService) {
        this.cityService = cityService;
    }

    @Test
    public void testCityAPI() throws Exception {
        assertEquals("Frankfurt",cityService.getCityCoordinates("frankfurt").getName());
    }
}
