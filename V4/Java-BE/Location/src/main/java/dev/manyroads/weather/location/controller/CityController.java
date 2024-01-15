package dev.manyroads.weather.location.controller;

import dev.manyroads.weather.location.service.CityService1;
import dev.manyroads.weather.shared.model.City;
import dev.manyroads.weather.location.service.CityService2;
import dev.manyroads.weather.location.util.ResetBackupCityService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
public class CityController {

    static Logger logger = Logger.getLogger(CityController.class.getName());
    // Boolean flag to remember if back-up city service for retrieving city coordinates
    public static boolean backupCityService = false;
    static int delayBackupCityservice = 60;

    CityService1 cityService;
    CityService2 cityService2;

    public CityController(CityService1 cityService, CityService2 cityService2) {
        this.cityService = cityService;
        this.cityService2 = cityService2;
    }

    /*
    Method receives location request with parameter "name" in the uri.
    If first city location api is not responding, a second city location api will be contacted automatically
    Example: curl '-X' 'GET' 'http://localhost:8081/city?name=madrid'
    */
    @GetMapping(
            value = "/city",
            produces = "application/json"
    )
    public City getCityLocation(@RequestParam(value = "name") String cityName)
            throws RestClientException, Exception {

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();

        if (!backupCityService) {

            try {
                city = cityService.getCityCoordinates(cityName);

            } catch (RestClientException ex) {
                logger.log(Level.SEVERE, "E1 Foutje ophalen coordinaten CityService1: " + ex.getMessage());

                // Set flag and timer to reset CityService backup flag
                setBackupCityServiceFlagAndResetTimer(delayBackupCityservice);

                //throw new RestClientException("E2 Foutje ophalen coordinaten city. ");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "E3 Algemene fout ophalen coordinaten  CityService1: " + ex.getMessage());

                // Set flag and timer to reset CityService backup flag
                setBackupCityServiceFlagAndResetTimer(delayBackupCityservice);

                //throw new Exception("E4 Algemene fout ophalen coordinaten city. ");
            }
        }
        if (backupCityService) {
            // Try back-up CityService
            city = getBackupCityServiceCoordinates(cityName);
        }

        logger.info("Returns city: " + city);

        return city;
    }

    // --- Sub-methods ---
    /*
    Start up backup City location API
     */
    City getBackupCityServiceCoordinates(String cityName) throws RestClientException, Exception {

        logger.info("Started CityService 2");

        return cityService2.getCityCoordinates(cityName);
    }

    /*
    Start backup flag and timer to reset backup City location API flag
     */
    void setBackupCityServiceFlagAndResetTimer(int delay) {

        ResetBackupCityService resetBackupCityService = new ResetBackupCityService();
        Timer timer = new Timer();

        // Set Citservice backup flag
        this.backupCityService = true;

        logger.info("Set backup CityService: " + this.backupCityService + " When: " + new Date());
        timer.schedule(resetBackupCityService, delay * 1000);

    }
}
