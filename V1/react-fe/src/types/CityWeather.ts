// interface for CityWeather DTO from Java backend
export interface CityWeather{
    name: string,
    country: string,
    temperature: number,
    windspeed: number,
    timezone: string,    
    time: string
}