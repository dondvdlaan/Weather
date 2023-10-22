package dev.manyroads.weather.cityweather.service;

import dev.manyroads.weather.cityweather.model.CityWeather;
import dev.manyroads.weather.cityweather.repository.CityWeatherEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-22T19:34:48+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class CityWeatherMapperImpl implements CityWeatherMapper {

    @Override
    public CityWeatherEntity apiToEntity(CityWeather cityWeather) {
        if ( cityWeather == null ) {
            return null;
        }

        CityWeatherEntity cityWeatherEntity = new CityWeatherEntity();

        cityWeatherEntity.setName( cityWeather.getName() );
        cityWeatherEntity.setCountry( cityWeather.getCountry() );
        cityWeatherEntity.setTemperature( cityWeather.getTemperature() );
        cityWeatherEntity.setWindspeed( cityWeather.getWindspeed() );
        cityWeatherEntity.setTimezone( cityWeather.getTimezone() );
        cityWeatherEntity.setTime( cityWeather.getTime() );

        return cityWeatherEntity;
    }

    @Override
    public CityWeather entityToApi(CityWeatherEntity cityWeatherEntity) {
        if ( cityWeatherEntity == null ) {
            return null;
        }

        CityWeather cityWeather = new CityWeather();

        cityWeather.setName( cityWeatherEntity.getName() );
        cityWeather.setCountry( cityWeatherEntity.getCountry() );
        cityWeather.setTemperature( cityWeatherEntity.getTemperature() );
        cityWeather.setWindspeed( cityWeatherEntity.getWindspeed() );
        cityWeather.setTimezone( cityWeatherEntity.getTimezone() );
        cityWeather.setTime( cityWeatherEntity.getTime() );

        return cityWeather;
    }
}
