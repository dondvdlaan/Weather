package dev.manyroads.weather.composite.controller;

import dev.manyroads.weather.composite.service.CityService;
import dev.manyroads.weather.composite.service.WeatherService;
import dev.manyroads.weather.constants.ApiConstants;
import dev.manyroads.weather.model.City;
import dev.manyroads.weather.model.CityWeather;
import dev.manyroads.weather.model.WeatherRaw;
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
    Method receives weather request for city with parameter "name" in the uri.
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
        Mono<CityWeather> monoCityWeather = Mono.just(new CityWeather());

        // If requested city is empty, return default CityWeather
        if(cityName.isEmpty()) return errorCityWeather();

        // Get city coordinates
        try{
            city = cityService.getCityCoordinates(cityName);
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"Foutje coordinaten ophalen: " + ex.getMessage());
            return errorCityWeather();
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Algemene fout ophalen coordinaten: " + ex.getMessage());
            return errorCityWeather();
            // TODO: implement rety / circuitbreaker, because the API returns often 500 error
        }
        logger.info("City aangekomen: " + city);

        // Check if city name is not empty
        if(!city.getName().equals(ApiConstants.DEFAULT_CITY_STRING)){

            // Get City weather
            try{
                monoWeather = weatherService.getWeatherForecast(city.getLatitude(), city.getLongitude());
            }catch(Exception ex){
                logger.log(Level.SEVERE,"Failure retieving weather");
                return errorCityWeather();
            }
            monoWeather.subscribe(m-> logger.info("Goede weer aangekomen:" + m));

            // Combine city and weather results in to CityWeather
            City finalCity = city;
             monoCityWeather = monoWeather.flatMap(m ->{
                CityWeather cityWeather = new CityWeather();
                    cityWeather.setName(finalCity.getName());
                    cityWeather.setCountry(finalCity.getCountry());
                    cityWeather.setTemperature(m.getCurrent_weather().getTemperature());
                    cityWeather.setWindspeed(m.getCurrent_weather().getWindspeed());
                    cityWeather.setTimezone(m.getTimezone());
                    cityWeather.setTime(m.getCurrent_weather().getTime());

            return Mono.just(cityWeather);
            });
        }

        monoCityWeather.subscribe(m-> logger.info("monoCityWeather: " + m));

        return monoCityWeather;
    }

    // ---- Submethods ----
    /*
    Method to return default Mono<CityWeather> in case of error
     */
    Mono<CityWeather> errorCityWeather(){
        return Mono.error(new Error("Mono Error"));
        //return Mono.just(new CityWeather());
    }
}
