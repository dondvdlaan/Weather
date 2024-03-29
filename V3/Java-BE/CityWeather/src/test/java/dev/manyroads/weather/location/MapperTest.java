package dev.manyroads.weather.location;

import dev.manyroads.weather.location.repository.CityWeatherEntity;
import dev.manyroads.weather.location.service.CityWeatherMapper;

import dev.manyroads.weather.shared.model.CityWeather;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing mapper conversions
 */
public class MapperTest {

    // Creating instance of Mapper(mapper itself is an interface
    CityWeatherMapper mapper = Mappers.getMapper(CityWeatherMapper.class);

    @Test
    public void mapperTest(){

        assertNotNull(mapper);

        CityWeather cityWeather = new CityWeather("Adam", "NL", 20D, 2D, "GMT", new Date().toString() );

        CityWeatherEntity cityWeatherEntity = mapper.apiToEntity(cityWeather);

        assert cityWeather.getName().equals(cityWeatherEntity.getName());
        assert cityWeatherEntity.getCityWeatherID() == null;
        assertEquals(cityWeather.getCountry(), cityWeatherEntity.getCountry());
        assertNull(cityWeatherEntity.getCityWeatherID());

        CityWeather cityWeather2 = mapper.entityToApi(cityWeatherEntity);

        assert cityWeather2.getName().equals(cityWeatherEntity.getName());

    }
}
