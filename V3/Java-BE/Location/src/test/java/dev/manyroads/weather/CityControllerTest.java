package dev.manyroads.weather;


import dev.manyroads.weather.shared.model.City;
import dev.manyroads.weather.cityweather.service.CityService1;
import dev.manyroads.weather.cityweather.service.CityService2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
/*
Creates a web application context (reactive or servlet based) and sets a
server.port=0 Environment property (which usually triggers listening on a random port).
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CityControllerTest
{
    static final String CITY = "Berlin";
    static final String NONE_EXISTING_CITY = "wwww";
    @MockBean
    CityService1 cityService1;
    @MockBean
    CityService2 cityService2;
    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setup() throws Exception {
        City city = new City();
        city.setName("Berlin");

        // Mocking
        //when(cityService1.getCityCoordinates(CITY))
        //        .thenReturn(city);
        when(cityService2.getCityCoordinates(CITY))
                .thenReturn(city);
        when(cityService1.getCityCoordinates(CITY))
                .thenThrow(new RuntimeException("Internal server error"));
    }

    @Test
    public void cityControllerTest() throws Exception {

        client
                .get()
                .uri("/city?name=" + CITY)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$.name").isEqualTo(CITY);
    }
   /*
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
