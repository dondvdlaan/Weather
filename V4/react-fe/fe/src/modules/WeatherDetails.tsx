import { Box, Card, CardBody, Grid, Paragraph, Text } from "grommet";
import { CityWeather } from "../types/CityWeather"
import { useEffect } from "react";


interface Props {
    cityWeather: CityWeather | undefined
}

/**
 * Function for displaying details weather report
 * @param props 
 * @returns 
 */
export const WeatherDetails = (props: Props) => {

    // --- Constants and variables ----
    let cityWeather: CityWeather | undefined = props.cityWeather;
    let localDate = new Date(0)
    const dateTimeFormat = new Intl.DateTimeFormat("de-DE", { dateStyle: 'medium', timeStyle: 'medium' })

    if (cityWeather) {
        localDate = new Date(cityWeather?.time)
    }

    let timeNow = dateTimeFormat.format(new Date())
    let weatherTime = dateTimeFormat.format(localDate)

    return (
        <Grid rows="small" columns={{ count: 'fit', size: 'small' }} gap="small">

            <Card pad="small" gap="medium" background="light-1">
                <Text size="small">Country: {cityWeather?.country}</Text>
                <Text size="small">Temperature: {cityWeather?.temperature}</Text>
                <Text size="small">Windspeed: {cityWeather?.windspeed} km/h</Text>
                <Text size="small"> W code: {cityWeather?.weathercode}</Text>
            </Card>

            <Card pad="medium" gap="medium" background="light-1">
                <Text>Time of Display: </Text>
                <Text>{timeNow}</Text>
                <Text size="small">Day / Night: {cityWeather?.is_day ? "day" : "night"}</Text>
            </Card>

            <Card pad="medium" gap="medium" background="light-1">
                <Text>Local TimeStamp: </Text>
                <Text>{weatherTime}</Text>
                <Text>Original TimeStamp:</Text>
                <Text>{cityWeather?.time}</Text>
            </Card>

        </Grid>
    );

}