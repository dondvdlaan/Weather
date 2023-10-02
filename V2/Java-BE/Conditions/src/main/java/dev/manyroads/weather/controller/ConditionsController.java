package dev.manyroads.weather.controller;

import dev.manyroads.weather.service.WeatherService;
import dev.manyroads.weather.model.WeatherRaw;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
public class ConditionsController {

    Logger logger = Logger.getLogger(ConditionsController.class.getName());

    WeatherService weatherService;

    public ConditionsController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /*
    Method receives weather request with parameter "name" in the uri.
    Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
     */
    @GetMapping(
            value="/conditions/{latitude}/{longitude}",
            produces = "application/json"
    )
    public Mono<WeatherRaw> getCityWeather(@PathVariable double latitude,
                                        @PathVariable double longitude){

        logger.info("latitude: " + latitude + " longitude: " + longitude);

        // Initialise variables
        Mono<WeatherRaw> monoWeatherRaw = Mono.empty();

        // Get City weather
        try{
            monoWeatherRaw = weatherService.getWeatherForecast(latitude, longitude);
        }catch(Exception ex){
            logger.log(Level.SEVERE,"Failure retieving weather");
            return returnDefault();
        }

        monoWeatherRaw.subscribe(m-> logger.info("monoWeatherRaw: " + m));

        return monoWeatherRaw;
    }

    // ---- Submethods ----
    /*
    Method to return default Mono<CityWeather> in case of error
     */
    Mono<WeatherRaw> returnDefault(){
        return Mono.just(new WeatherRaw());
    }
}
