package dev.manyroads.weather.cityweather.config;

import dev.manyroads.weather.cityweather.model.CityWeather;
import dev.manyroads.weather.cityweather.model.Event;
import dev.manyroads.weather.cityweather.service.CityWeatherService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class StreamingConfig {

    CityWeatherService cityWeatherService;

    public StreamingConfig(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    @Bean
    Consumer<Event<Integer, CityWeather>> messageProcessor(){
        return e -> {
            if(e.getType() == Event.Type.Save)
                cityWeatherService.saveCityWeather(e.getData())
                        // Block to return ^confirmation /
                        // error message
                        .block();

        };
    }


}
