package dev.manyroads.weather.location;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
    // Creating a logger
    private static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main( String[] args )
    {
        SpringApplication.run(Main.class, args);
    }
}
