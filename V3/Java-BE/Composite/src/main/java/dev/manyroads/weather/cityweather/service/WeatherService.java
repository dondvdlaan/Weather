package dev.manyroads.weather.cityweather.service;

import dev.manyroads.weather.event.Event;
import dev.manyroads.weather.model.City;
import dev.manyroads.weather.model.WeatherRaw;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static dev.manyroads.weather.event.Event.Type.GET;

@Service
public class WeatherService {

    Logger logger = LogManager.getLogger(WeatherService.class.getName());

    String scheme;
    String weatherHost;
    String weatherPort;
    String weatherPath;
    String weatherUrl;
    //Spring Streambridge, message is published to processor
    private final StreamBridge streamBridge;
    // Reactor scheduler, provides thread for Streambridge
    private final Scheduler publishEventScheduler;

    static String latitude = "latitude=";
    static String longitude = "&longitude=";
    static String current_weather = "&current_weather=true";
    static String uriLight =
            "https://api.open-meteo.com/v1/forecast?latitude=40.4165&longitude=-3.7026";

    public WeatherService(
            @Value("${scheme}") String scheme,
            @Value("${weatherHost}") String weatherHost,
            @Value("${weatherPort}") String weatherPort,
            @Value("${weatherPath}") String weatherPath,
            StreamBridge streamBridge,
            Scheduler publishEventScheduler
    ) {
        this.scheme = scheme;
        this.weatherHost = weatherHost;
        this.weatherPort = weatherPort;
        this.weatherPath = weatherPath;
        this.streamBridge = streamBridge;
        this.publishEventScheduler = publishEventScheduler;

        // Compose URL
        weatherUrl = scheme + weatherHost + weatherPort + weatherPath;
    }

    /*
    Methode to obtain the weather conditions of a city based on the latitude and logitude coordinates
     */
    public Mono<WeatherRaw> getWeatherForecast (double latitude, double longitude)
            throws Exception{

        /*
        Compose uri:
        EXAMPLE: "curl '-X' 'GET' 'http://localhost:8081/conditions/52.35/4.9166'"
         */
        String weatherUri = weatherUrl + "/" + latitude + "/" + longitude;
        logger.info("weatherUri: " + weatherUri);

        // Prepare City DTO
        City city = new City();
                city.setLatitude(latitude);
                city.setLongitude(longitude);

        // Initialise variables
        Mono<WeatherRaw> monoWeatherRaw = Mono.fromCallable(()->{

            sendMessage("conditions-out-0",new Event(GET,"noKey",city));
        })

        /*
        // Compose asynchronous webClient
        WebClient webClient = WebClient.create(weatherUri);

        try {

            RestTemplate request = new RestTemplate();
            ResponseEntity<WeatherRaw> res = request.getForEntity(uri,WeatherRaw.class);
            System.out.println("res.getBody(): " + res.getBody().getCurrent_weather().getTemperature());

            monoWeatherRaw = webClient
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherRaw.class);
        } catch(Exception ex) {
            logger.error("Weblient failt: " + ex.getMessage());
            throw new Exception("");
        }
        */

        // Print
        monoWeatherRaw.subscribe(m-> logger.info("monoWeatherRaw: " + m));

        return monoWeatherRaw;
    }
    // ---- Sub-methods ----
    /*
    Methode to prepare message and hand it over to streamBridge
     */
    private void sendMessage(String bindingName, Event event) {

        logger.log(Level.DEBUG, "Sending a " + event.getType() + " message to " + bindingName);

        Message message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();
        streamBridge.send(bindingName, message);
    }
}


