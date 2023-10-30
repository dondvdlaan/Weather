package dev.manyroads.weather.composite;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class Main {
    // Creating a logger
    //private static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*
    Scheduler usable by publishOn or subscribeOn
    Optimized for longer executions, an alternative for blocking tasks
    where the number of active tasks (and threads) is capped
    */
    @Bean
    public Scheduler publishEventScheduler() {

        System.out.println("*** In publishEventScheduler ***");
        return Schedulers.newBoundedElastic(3, 3, "publish-pool");
    }
}
