package dev.manyroads.weather.cityweather.util;

import dev.manyroads.weather.cityweather.controller.CityController;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;

public class ResetBackupCityService extends TimerTask {

    static Logger logger = Logger.getLogger(ResetBackupCityService.class.getName());

    @Override
    public void run() {
        CityController.backupCityService = false;
        logger.info("Reset backup CityService: " + CityController.backupCityService + " When: " + new Date());
    }
}
