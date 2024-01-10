package dev.manyroads.weather.composite.security.repositories;

import dev.manyroads.weather.composite.security.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUserName(String userName);
}
