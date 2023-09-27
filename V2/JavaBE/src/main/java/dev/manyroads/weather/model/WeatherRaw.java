package dev.manyroads.weather.model;

/**
 * Model WeatherRaw is interface or DTO with the API for obtaining the weather conditions based of the
 * latitude and longitude coordinates of the required city
 */
public class WeatherRaw {

    static final String DEFAULT_STRING = "<nothingToSeeHer>";
    static final double DEFAULT_DOUBLE = -1D;
    static final int DEFAULT_INT = -1;

    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private double elevation;
    private Weather current_weather;

    public WeatherRaw() {
        this.latitude = DEFAULT_DOUBLE;
        this.longitude = DEFAULT_DOUBLE;
        this.generationtime_ms = DEFAULT_DOUBLE;
        this.utc_offset_seconds = DEFAULT_INT;
        this.timezone = DEFAULT_STRING;
        this.timezone_abbreviation = DEFAULT_STRING;
        this.elevation = DEFAULT_DOUBLE;
        this.current_weather = new Weather();
    }

     public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }

    public int getUtc_offset_seconds() {
        return utc_offset_seconds;
    }

    public void setUtc_offset_seconds(int utc_offset_seconds) {
        this.utc_offset_seconds = utc_offset_seconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_abbreviation() {
        return timezone_abbreviation;
    }

    public void setTimezone_abbreviation(String timezone_abbreviation) {
        this.timezone_abbreviation = timezone_abbreviation;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public Weather getCurrent_weather() {
        return current_weather;
    }

    public void setCurrent_weather(Weather current_weather) {
        this.current_weather = current_weather;
    }

    @Override
    public String toString() {
        return "WeatherRaw{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", generationtime_ms=" + generationtime_ms +
                ", utc_offset_seconds=" + utc_offset_seconds +
                ", timezone='" + timezone + '\'' +
                ", timezone_abbreviation='" + timezone_abbreviation + '\'' +
                ", elevation=" + elevation +
                ", current_weather=" + current_weather +
                '}';
    }
}
