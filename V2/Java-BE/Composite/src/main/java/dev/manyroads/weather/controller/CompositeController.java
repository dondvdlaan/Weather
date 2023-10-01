package dev.manyroads.weather.controller;

import dev.manyroads.weather.model.City;
import dev.manyroads.weather.model.CityWeather;
import dev.manyroads.weather.model.WeatherRaw;
import dev.manyroads.weather.service.CityService;
import dev.manyroads.weather.service.WeatherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CompositeController {

    Logger logger = Logger.getLogger(CompositeController.class.getName());

    CityService cityService;
    WeatherService weatherService;

    public CompositeController(CityService cityService, WeatherService weatherService) {
        this.cityService = cityService;
        this.weatherService = weatherService;
    }

    /*
    Method receives weather request with parameter "name" in the uri.
    Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
     */
    @GetMapping(
            value="/city",
            produces = "application/json"
    )
    public Mono<CityWeather> getCityWeather(@RequestParam( value = "name") String cityName){

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();
        Mono<WeatherRaw> monoWeather = Mono.empty();

        // Get city coordinates
        try{
            city = cityService.getCityCoordinates(cityName);
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"Foutje coordinaten ophalen");
            //return returnDefault();
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Foutje converteren JSON");
           // return returnDefault();
            // TODO: implement rety / circuitbreaker, because the API returns often 500 error
        }
        logger.info("City aangekomen: " + city);

        // Get City weather
        try{
            monoWeather = weatherService.getWeatherForecast(city.getLatitude(), city.getLongitude());
        }catch(Exception ex){
            logger.log(Level.SEVERE,"Failure retieving weather");
            //return returnDefault();
        }
        monoWeather.subscribe(m-> logger.info("Goede weer aangekomen:" + m));

        // Combine city and weather results in to CityWeather
        City finalCity = city;
        Mono<CityWeather> monoCityWeather = monoWeather.flatMap(m ->{
            CityWeather cityWeather = new CityWeather();
                    cityWeather.setName(finalCity.getName());
                    cityWeather.setCountry(finalCity.getCountry());
                    cityWeather.setTemperature(m.getCurrent_weather().getTemperature());
                    cityWeather.setWindspeed(m.getCurrent_weather().getWindspeed());
                    cityWeather.setTimezone(m.getTimezone());
                    cityWeather.setTime(m.getCurrent_weather().getTime());

           return Mono.just(cityWeather);
        });

        monoCityWeather.subscribe(m-> logger.info("monoCityWeather: " + m));

        return monoCityWeather;
    }

    // ---- Submethods ----
    /*
    Method to return default Mono<CityWeather> in case of error
     */
    Mono<CityWeather> returnDefault(){
        return Mono.just(new CityWeather());
    }
}
