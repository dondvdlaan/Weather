package dev.manyroads.weather.composite;


import dev.manyroads.weather.shared.model.CityWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//import org.springframework.cloud.stream.binder.test.OutputDestination;
//import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.messaging.Message;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest(
        webEnvironment = RANDOM_PORT)
// To be able to use OutputDestination, we add TestChannelBinderConfiguration
//@Import({TestChannelBinderConfiguration.class})
public class CityWeatherServiceTest {

    Logger logger = Logger.getLogger(CityWeatherServiceTest.class.getName());

    //@Autowired
    //OutputDestination outputDestination;

    @Autowired
    WebTestClient webTestClient;


    void setUp() {

    }

    @Test
    public void storeCityWeatherTest() {

        CityWeather cityWeather =
                new CityWeather("Adam", "NL", 15D, 2D, "GMT", new Date().toString());

        webTestClient
                .post()
                .uri("/storeCity")
                .body(Mono.just(cityWeather), CityWeather.class)
                .exchange()
                .expectStatus().isOk();

        final List<String> cityWeatherMessages = getMessages("cityweather");

        // Assert one create cityWeather event queued up
        assertEquals(1, cityWeatherMessages.size());
    }

    // Submethods
    private List<String> getMessages(String bindingName) {

        List<String> messages = new ArrayList<>();
        boolean anyMoreMessages = true;

        while (anyMoreMessages) {
            Message<byte[]> message = getMessage(bindingName);

            if (message == null) {
                anyMoreMessages = false;

            } else {
                messages.add(new String(message.getPayload()));
            }
        }
        return messages;
    }

    private Message<byte[]> getMessage(String bindingName) {
        try {
            //return outputDestination.receive(0, bindingName);
            return null;
        } catch (NullPointerException npe) {
            // If the messageQueues member variable in the target object contains no queues when the receive method is called, it will cause a NPE to be thrown.
            // So we catch the NPE here and return null to indicate that no messages were found.
            logger.log(Level.SEVERE, "getMessage() received a NPE with binding " + bindingName);

            return null;
        }
    }
}
