package dev.manyroads.weather.composite.security.repositories;

import dev.manyroads.weather.composite.security.models.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
    RefreshToken findByRefreshToken(String refreshToken);
}
