package dev.manyroads.weather.conditions.service;

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

    String weatherUrl;

    static String latitude = "latitude=";
    static String longitude = "&longitude=";
    static String current_weather = "&current_weather=true";
    static String uriLight =
            "https://api.open-meteo.com/v1/forecast?latitude=40.4165&longitude=-3.7026";

    public WeatherService(
            @Value("${weatherUrl}") String weatherUrl) {
        this.weatherUrl = weatherUrl;
    }

    /*
    Methode to obtain the weather conditions of a city based on the latitude and logitude coordinates
     */
    public Mono<WeatherRaw> getWeatherForecast (double latitude, double longitude)
            throws Exception{

        /*
        Compose uri:
        EXAMPLE: "https://api.open-meteo.com/v1/forecast?latitude=40.4165&longitude=-3.7026&current_weather=true"
         */
        String weatherUri = weatherUrl + this.latitude + latitude + this.longitude + longitude + current_weather;
        logger.info("weatherUri: " + weatherUri);

        // Initialise variables
        Mono<WeatherRaw> monoWeatherRaw = Mono.empty();

        // Compose asynchronous webClient
        WebClient webClient = WebClient.create(weatherUri);

        try {
            /*
            RestTemplate request = new RestTemplate();
            ResponseEntity<WeatherRaw> res = request.getForEntity(uri,WeatherRaw.class);
            System.out.println("res.getBody(): " + res.getBody().getCurrent_weather().getTemperature());
            */
            monoWeatherRaw = webClient
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherRaw.class);
        } catch(Exception ex) {
            logger.error("Weblient failt: " + ex.getMessage());
            throw new Exception("");
        }
        // Print
        monoWeatherRaw.subscribe(m-> logger.info("Mono: " + m));

        return monoWeatherRaw;
    }
}


