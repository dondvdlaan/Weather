import { Box, Card, CardBody, Grid, Paragraph, Text } from "grommet";
import { CityWeather } from "../../types/CityWeather"
import { WeatherSymbol } from "./WeatherSymbol";

interface Props {
    cityWeather: CityWeather
}

/**
 * Function for displaying details weather report
 * @param props 
 * @returns 
 */
export const WeatherDetails = (props: Props) => {

    // --- Constants and variables ----
    let cityWeather: CityWeather  = props.cityWeather;
    let localDate = new Date(0)
    const dateTimeFormat = new Intl.DateTimeFormat("de-DE", { dateStyle: 'medium', timeStyle: 'medium' })

    if (cityWeather) {
        localDate = new Date(cityWeather?.time)
    }

    let timeNow = dateTimeFormat.format(new Date())
    let weatherTime = dateTimeFormat.format(localDate)

    return (
        <>
            <Grid rows="xsmall" columns={{ count: 'fit', size: 'small' }} gap="small">
                <Card pad="small" gap="small" background="light-1">
                    <Text >Country: {cityWeather?.country} </Text>
                    <Text >City: {cityWeather?.name}</Text>
                </Card>
                <Card pad="medium" gap="small" background="light-1">
                    <Text >Temperature:</Text>
                    <Text >{cityWeather?.temperature} °C</Text>
                </Card>
                <Card pad="medium" gap="small" background="light-1">
                    <Text >Windspeed:</Text>
                    <Text >{cityWeather?.windspeed} km/h</Text>
                </Card>
                <WeatherSymbol weatherCode={cityWeather.weathercode} />
                <Card pad="medium" gap="small" background="light-1">
                    <Text>Sample time: </Text>
                    <Text>{weatherTime}</Text>
                </Card>
            </Grid>
        </>

    );

}