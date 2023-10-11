package dev.manyroads.weather;


import dev.manyroads.weather.cityweather.service.CityService2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
/*
Creates a web application context (reactive or servlet based) and sets a
server.port=0 Environment property (which usually triggers listening on a random port).
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CityService2Test
{
    static final String CITY = "Berlin";
    static final String NONE_EXISTING_CITY = "wwww";

    @Autowired
    CityService2 cityService2;
    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setup() throws Exception {}


    @Test
    public void cityService2Test() throws Exception {

        assertEquals(CITY, cityService2.getCityCoordinates(CITY).getName()  );
    }

}
