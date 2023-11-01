package dev.manyroads.weather.composite;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
// background task executor is created
@EnableScheduling
public class Main {

    // Creating a logger
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        // Starting SpringBoot application
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
