package dev.manyroads.weather.composite.scheduler;

import dev.manyroads.weather.composite.service.CityWeatherService;
import dev.manyroads.weather.shared.model.CityWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Class CityWeatherTrending starts automatically by way of @EnableScheduling annotation in main. As soon as the start signal
 * and the city name is received from the CompositeController, this Class begins trending to the DB.
 */
@Service
public class CityWeatherTrending {

    private static Logger log = LoggerFactory.getLogger(CityWeatherTrending.class);
    // Start signal is set by the CompositeController startTrending methode
    private static boolean trendStarted = false;
    // City name is set by the CompositeController startTrending methode
    private static String cityName = "noCityName";

    // Scheduler interval in ms (not used)
    private final int schedulerRate = 30000;

    CityWeatherService cityWeatherService;

    public CityWeatherTrending(CityWeatherService cityWeatherService) {
        this.cityWeatherService = cityWeatherService;
    }

    public static void setTrendStarted(boolean trendStarted) {
        CityWeatherTrending.trendStarted = trendStarted;
    }

    public static void setCityName(String cityName) {
        CityWeatherTrending.cityName = cityName;
    }

    /**
     * Method trending is triggered every x s (fixedrate)
     */
    // Scheduler for treding every 30 min
    //@Scheduled(cron = "* 0 30 * * *")
    @Scheduled(fixedRate = 1800_000)
    public void trending() {

        log.debug("------- Checks for Start Signal from CompositeController -------------------");
        log.debug(" ------- Start Signal: " + trendStarted);
        log.debug(" ------- City name: " + cityName);

        if (trendStarted) {

            log.info("------- Started Trending -------------------");
            log.info("------- CityName: " + cityName);

            try {
                log.info("------- Retrieve CityWeather -------------------");
                Mono<CityWeather> mono = cityWeatherService.getCityWeather(cityName);

                // Convert Mono to Class CityWeather
                CityWeather cityWeather = mono.block();
                log.info("------- Retrieved CityWeather: " + cityWeather);

                log.info("------- Storing CityWeather -------------------");
                Mono<CityWeather> mono2 = cityWeatherService.storeCityWeather(cityWeather);

                log.info("------- Stored CityWeather -------------------");
                mono2.subscribe(m -> System.out.println("------- Stored CityWeather: " + m));

            } catch (Exception ex) {
                log.error("Error retrieving CityWeather: " + ex.getMessage());
            }
        }
    }
    // Submethods
}
