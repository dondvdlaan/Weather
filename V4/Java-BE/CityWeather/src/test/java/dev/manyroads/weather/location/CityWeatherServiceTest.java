package dev.manyroads.weather.location;

import dev.manyroads.weather.location.repository.CityWeatherRepository;
import dev.manyroads.weather.location.service.CityWeatherService;
import dev.manyroads.weather.shared.model.CityWeather;
import dev.manyroads.weather.shared.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;
import java.util.function.Consumer;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CityWeatherServiceTest extends MongoDbTestBase {

    @Autowired
    CityWeatherRepository cityWeatherRepository;
    @Autowired
    CityWeatherService cityWeatherService;

    @Autowired
    @Qualifier("messageProcessor")
    private Consumer<Event<Integer, CityWeather>> messageProcessor;

    @BeforeEach
    void setupDb() {
        cityWeatherRepository.deleteAll().block();
    }

    @Test
    public void saveCityWeatherTest(){

        CityWeather cityWeather = new CityWeather("Adam", "NL", 20D, 2D, "GMT", new Date().toString() );

        Mono<CityWeather> mono = cityWeatherService.saveCityWeather(cityWeather);

        StepVerifier.create(mono)
                .expectNextMatches(c->c.getName().equals(cityWeather.getName()));
    }
    @Test
    public void messageProcessorTest(){

        CityWeather cityWeather =
                new CityWeather("Rdam","NL", 15d,1.4d,"GMT",new Date().toString());
        Event event = new Event(Event.Type.Save,1,cityWeather);
        messageProcessor.accept(event);

        assertNotNull(cityWeatherRepository.findByNameIgnoreCase("Rdam").blockFirst());
        assert(cityWeatherRepository.findByNameIgnoreCase("Rdam").blockFirst().getName().equals("Rdam"));

    }
}
