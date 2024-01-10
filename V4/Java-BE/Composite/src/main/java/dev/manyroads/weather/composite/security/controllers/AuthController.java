package dev.manyroads.weather.composite.security.controllers;

import dev.manyroads.weather.composite.security.payloads.JwtResponse;
import dev.manyroads.weather.composite.security.payloads.LoginRequest;
import dev.manyroads.weather.composite.security.payloads.TokenRefreshRequest;
import dev.manyroads.weather.composite.security.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*
Class to authenticate a user, hand out jwt tokens and to refresh the jwt token when it expires
 */
@RestController
public class AuthController {

    Logger logger = LogManager.getLogger(AuthController.class);

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> authenticationLogin(@RequestBody LoginRequest loginRequest) {

        logger.debug("/login");

        return authService.authenticateLogin(loginRequest);
    }

    /*
    Method to handle request for a refreshed token. The refresh token expired exception is taken care of by the
    GlobalControllerExceptionHandler.
     */
    @PostMapping("/refreshtoken")
    public Mono<ResponseEntity<JwtResponse>> refreshJWTToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {

        logger.debug("/refreshtoken");

        logger.debug("Handling refresh token");
        return authService.handleTokenRefresh(tokenRefreshRequest);
    }

    // **** Testing ***
    @PostMapping("/findrefreshtoken")
    public Mono<ResponseEntity> findRefreshJWTToken(@RequestBody String refreshToken) {

        logger.debug("/refreshtoken");

        return authService.findRefreshToken(refreshToken);
    }
    // **** END Testing ***
}
