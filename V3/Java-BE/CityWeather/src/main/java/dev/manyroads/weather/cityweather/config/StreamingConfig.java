package dev.manyroads.weather.cityweather.config;

import dev.manyroads.weather.cityweather.service.CityWeatherService;

import dev.manyroads.weather.shared.model.CityWeather;
import dev.manyroads.weather.shared.event.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.logging.Logger;

@Configuration
public class StreamingConfig {

    Logger logger = Logger.getLogger(StreamingConfig.class.getName());

    CityWeatherService cityWeatherService;

    public StreamingConfig(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    @Bean
    Consumer<Event<String, CityWeather>> messageProcessor(){

        return e -> {
            logger.info("In messageProcessor(): " + e.toString());

            cityWeatherService.saveCityWeather(e.getData())
                    // Block to return ^confirmation / error message
                    .block();
/*
            if(e.getType() == Event.Type.Save)

                logger.info("In Event.Type.Save..");
                cityWeatherService.saveCityWeather(e.getData())
                        // Block to return ^confirmation / error message
                        .block();

 */

        };
    }


}
