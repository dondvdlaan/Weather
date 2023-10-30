package dev.manyroads.weather.composite.service;

import dev.manyroads.weather.shared.model.WeatherRaw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    Logger logger = LogManager.getLogger(WeatherService.class.getName());

    String scheme;
    String weatherHost;
    String weatherPort;
    String weatherPath;
    String weatherUrl;

    static String latitude = "latitude=";
    static String longitude = "&longitude=";
    static String current_weather = "&current_weather=true";
    static String uriLight =
            "https://api.open-meteo.com/v1/forecast?latitude=40.4165&longitude=-3.7026";

    public WeatherService(
            @Value("${scheme}") String scheme,
            @Value("${weatherHost}") String weatherHost,
            @Value("${weatherPort}") String weatherPort,
            @Value("${weatherPath}") String weatherPath
    ) {
        this.scheme = scheme;
        this.weatherHost = weatherHost;
        this.weatherPort = weatherPort;
        this.weatherPath = weatherPath;

        // Compose URL
        weatherUrl = scheme + weatherHost + weatherPort + weatherPath;
    }

    /*
        Methode to obtain the weather conditions of a city based on the latitude and logitude coordinates
         */
    public Mono<WeatherRaw> getWeatherForecast(double latitude, double longitude)
            throws Exception {

        /*
        Compose uri:
        EXAMPLE: "curl '-X' 'GET' 'http://localhost:8081/conditions/52.35/4.9166'"
         */
        String weatherUri = weatherUrl + "/" + latitude + "/" + longitude;
        logger.info("weatherUri: " + weatherUri);

        // Initialise variables
        Mono<WeatherRaw> monoWeatherRaw = Mono.empty();

        // Compose asynchronous webClient
        WebClient webClient = WebClient.create(weatherUri);

        try {
            monoWeatherRaw = webClient
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherRaw.class);
        } catch (Exception ex) {
            logger.error("Weblient failt: " + ex.getMessage());
            throw new Exception("");
        }
        // Print
        monoWeatherRaw.subscribe(m -> logger.info("monoWeatherRaw: " + m));

        return monoWeatherRaw;
    }
    // ---- Sub-methods ----

}


