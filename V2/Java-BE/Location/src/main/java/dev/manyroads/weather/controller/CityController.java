package dev.manyroads.weather.controller;

import dev.manyroads.weather.model.City;
import dev.manyroads.weather.service.CityService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CityController {

    static Logger logger = Logger.getLogger(CityController.class.getName());

    CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /*
        Method receives location request with parameter "name" in the uri.
        Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
         */
    @GetMapping(
            value="/city",
            produces = "application/json"
    )
    public City getCityLocation(@RequestParam( value = "name") String cityName){

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();

        // Get city coordinates
        try{
            city = cityService.getCityCoordinates(cityName);
        }catch(RestClientException ex){
            logger.log(Level.SEVERE,"Foutje coordinaten ophalen");
            return returnDefault();
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Foutje converteren JSON");
            return returnDefault();
            // TODO: implement rety / circuitbreaker, because the API returns often 500 error
        }

        logger.info("Returns city: " + city);

        return city;
    }

    // ---- Submethods ----
    /*
    Method to return default City in case of error
     */
    City returnDefault(){
        return new City();
    }
}
