package dev.manyroads.weather.composite.security.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*
Class for testing the application
 */
@RestController
public class TestController {

    Logger logger = LogManager.getLogger(TestController.class);

    @GetMapping("/securitytest")
    public Mono<String> testing(Mono<Authentication> auth) {

        logger.info("/securitytest");

        return auth.map(a -> "Holita " + a.getName());
    }

    @GetMapping("/securitytesttest2")
    public Mono<String> testing() {

        logger.info("/securitytesttest2");

        return Mono.just("Holita2");
    }
}
