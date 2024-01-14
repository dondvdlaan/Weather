package dev.manyroads.weather.composite.service;

import dev.manyroads.weather.shared.constants.ApiConstants;
import dev.manyroads.weather.shared.model.City;
import dev.manyroads.weather.shared.model.CityWeather;
import dev.manyroads.weather.shared.event.Event;
import dev.manyroads.weather.shared.model.WeatherRaw;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityWeatherService {

    Logger logger = Logger.getLogger(CityWeatherService.class.getName());
    String scheme;
    String cityWeatherHost;
    String cityWeatherPort;
    String cityWeatherPath;
    String cityWeatherUrl;
    CityService cityService;
    WeatherService weatherService;
    private final StreamBridge streamBridge;
    Scheduler publishEventScheduler;

    public CityWeatherService(
            @Value("${scheme}") String scheme,
            @Value("${cityWeatherHost}") String cityWeatherHost,
            @Value("${cityWeatherPort}") String cityWeatherPort,
            @Value("${cityWeatherPath}") String cityWeatherPath,
            CityService cityService,
            WeatherService weatherService,
            StreamBridge streamBridge,
            Scheduler publishEventScheduler) {

        this.cityService = cityService;
        this.weatherService = weatherService;
        this.streamBridge = streamBridge;
        this.publishEventScheduler = publishEventScheduler;

        // Compose URL
        cityWeatherUrl = scheme + cityWeatherHost + cityWeatherPort + cityWeatherPath;
    }

    /**
     * Method to retrieve city location and weather conditions
     *
     * @param cityName : String    :   City cityName
     * @return Mono<CityWeather>    : Mono      :   CityWeather
     */
    public Mono<CityWeather> getCityWeather(String cityName) {

        logger.info("cityName: " + cityName);

        // Initialise variables
        City city = new City();
        Mono<WeatherRaw> monoWeather = Mono.empty();
        Mono<CityWeather> monoCityWeather = Mono.just(new CityWeather());

        // If requested city is empty, return default CityWeather
        if (cityName.isEmpty()) return errorCityWeather();

        // Get city coordinates
        try {
            city = cityService.getCityCoordinates(cityName);
        } catch (RestClientException ex) {
            logger.log(Level.SEVERE, "Foutje coordinaten ophalen: " + ex.getMessage());
            return errorCityWeather();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Algemene fout ophalen coordinaten: " + ex.getMessage());
            return errorCityWeather();
        }
        logger.info("City aangekomen: " + city);

        // Check if city cityName is not empty
        if (!city.getName().equals(ApiConstants.DEFAULT_CITY_STRING)) {

            // Get City weather
            try {
                monoWeather = weatherService.getWeatherForecast(city.getLatitude(), city.getLongitude());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failure retieving weather");
                return errorCityWeather();
            }
            monoWeather.subscribe(m -> logger.info("Goede weer aangekomen:" + m));

            // Combine city and weather results in to CityWeather
            City finalCity = city;
            monoCityWeather = monoWeather.flatMap(m -> {
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

        monoCityWeather.subscribe(m -> logger.info("monoCityWeather: " + m));

        return monoCityWeather;

    }

    /**
     * Retrieve trend data for city from
     *
     * @param cityName : City for which trending is requested
     * @return Flux    : Trend data for city
     */
    public Flux<CityWeather> getTrendCityWeather(String cityName) {

          /*
        Composed uri:
        EXAMPLE: "curl '-X' 'GET' 'http://localhost:8081/getTrend/Adam'"
         */
        String cityWeatherUri = cityWeatherUrl + "/" + cityName;
        logger.info("getTrendCityWeather cityWeatherUri: " + cityWeatherUri);

        // Initialise variables
        Flux<CityWeather> fluxCityWeather = Flux.empty();

        // Compose asynchronous webClient
        WebClient webClient = WebClient.create(cityWeatherUri);

        logger.info("getTrendCityWeather Retrieving trend CityWeather fot :" + cityName);

        try {
            fluxCityWeather = webClient
                    .get()
                    .retrieve()
                    .bodyToFlux(CityWeather.class);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Weblient failt: " + ex.getMessage());
            return fluxCityWeather;
        }
        // Print
        fluxCityWeather.subscribe(m -> logger.info("**** Retrieved fluxCityWeather: " + m));

        return fluxCityWeather;
    }

    /**
     * Store cityWeather in DB
     *
     * @return
     */
    public Mono<CityWeather> storeCityWeather(CityWeather cityWeather) {

        logger.info("storeCityWeather cityWeather : " + cityWeather);

        return Mono.fromCallable(() -> {

                    sendMessage("cityweather-out-0",
                            new Event(Event.Type.Save, cityWeather.getName(), cityWeather));

                    return cityWeather;
                })
                /*
                Returning streamBridge(blocking code) is executed on a thread provided by
                publishEventScheduler bean
                */
                .subscribeOn(publishEventScheduler);

    }

    // ---- Submethods ----
    /*
    Method to return default Mono<CityWeather> in case of error
     */
    Mono<CityWeather> errorCityWeather() {
        return Mono.error(new Error("Mono Error"));
        //return Mono.just(new CityWeather());
    }

    /**
     * Build message and send by way of streamBridge to the messaging system, which will
     * publish it on the topic defined in the properties file
     *
     * @param bindingName
     * @param event
     */
    private void sendMessage(String bindingName, Event event) {

        Message message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getEventID())
                .build();

        System.out.println("** In sendMessage ** : " + message);
        streamBridge.send(bindingName, message);
    }
}
