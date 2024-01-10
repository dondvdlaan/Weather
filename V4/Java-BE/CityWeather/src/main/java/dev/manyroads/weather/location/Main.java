package dev.manyroads.weather.location;

import dev.manyroads.weather.location.repository.CityWeatherEntity;
import dev.manyroads.weather.location.repository.CityWeatherRepository;
import dev.manyroads.weather.location.service.CityWeatherMapper;
import dev.manyroads.weather.shared.model.CityWeather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class Main implements CommandLineRunner {
    // Creating a logger
    private static Logger logger = LogManager.getLogger(Main.class.getName());

    @Autowired
    CityWeatherRepository repo;
    @Autowired
    CityWeatherMapper mapper;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Filling DB");

        Thread.sleep(5000);

        repo.save(new CityWeatherEntity(
                        "NYC","GER",20D,1.5D,"GMT",new Date().toString()))
                .log()
                .onErrorMap(ex-> new Error("Foutje"))
                .subscribe();
        repo.save(new CityWeatherEntity(
                        "BoBodam","NL",20.1D,1.6D,"GMT",new Date().toString()))
                .subscribe();
        CityWeather c1 = new CityWeather("Adam", "NL", 15d, 2d, "GMT", new Date().toString());
        repo.save(mapper.apiToEntity(c1)).subscribe();
        CityWeather c2 = new CityWeather("Adam", "NL", 13.4d, 3d, "GMT",
                addHoursToJavaUtilDate(new Date(), 1).toString());
        repo.save(mapper.apiToEntity(c2)).subscribe();
        CityWeather c3 = new CityWeather("Adam", "NL", 17d, 4d, "GMT",
                addHoursToJavaUtilDate(new Date(), 2).toString());
        repo.save(mapper.apiToEntity(c3)).subscribe();
        CityWeather c4 = new CityWeather("Adam", "NL", 14.7d, 4d, "GMT",
                addHoursToJavaUtilDate(new Date(), 3).toString());
        repo.save(mapper.apiToEntity(c4)).subscribe();
        CityWeather c5 = new CityWeather("Adam", "NL", 19d, 4d, "GMT",
                addHoursToJavaUtilDate(new Date(), 4).toString());
        repo.save(mapper.apiToEntity(c5)).subscribe();
/*
 */

    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
