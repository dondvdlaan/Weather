package dev.manyroads.weather.composite;

import dev.manyroads.weather.composite.security.models.Role;
import dev.manyroads.weather.composite.security.models.User;
import dev.manyroads.weather.composite.security.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.HashSet;
import java.util.Set;

import static dev.manyroads.weather.composite.security.models.ERole.ROLE_USER;

@SpringBootApplication
// background task executor is created
@EnableScheduling
public class Main implements CommandLineRunner {

    // Creating a logger
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    UserRepository repository;

    public Main(UserRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class);

        // Checking the MySQL DB connection with the DOCKER MySQL container
        String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");

        logger.info("Connected to MySQL: " + mysqlUri);

        // Checking the Mongo DB connection with the DOCKER Mongo container
        String mongoHost = ctx.getEnvironment().getProperty("spring.data.mongodb.host");

        logger.info("Connected to Mongo: " + mongoHost);
    }

    /*
    add user to db
     */
    @Override
    public void run(String... args) throws Exception {

        logger.debug("Filling DB");

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ROLE_USER));

        Mono<User> monoUser = repository.save(new User(
                "tedje",
                "vanes",
                roles));

        monoUser.subscribe(m -> System.out.println(m));
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
