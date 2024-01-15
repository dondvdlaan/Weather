// interface for CityWeather DTO from Java backend
export interface CityWeather{
    name: string,
    country: string,
    temperature: number,
    windspeed: number,
    weathercode: number,
    is_day: boolean,
    timezone: string,    
    time: string
}