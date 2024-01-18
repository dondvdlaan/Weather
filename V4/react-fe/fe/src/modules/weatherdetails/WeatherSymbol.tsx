import React, { useState, useEffect, SetStateAction } from 'react';
import { Card, Grid, Text, Box, Button, CardHeader, CardBody } from "grommet";
//@ts-ignore
import * as Icons from "weather-icons-react"

/**
 * Component to display a symbol and short description of the current waether conditions
 * based on a code number received from the backend
 */
export const WeatherSymbol = (props: { weatherCode: number }) => {

    const [backGround, setBackGround] = useState("light-1")
    const [weatherDescription, setWeatherDescription] = useState("No description")
    const [symbol, setSymbol] = useState<any>()

    console.log("wc: ", props.weatherCode)

    /**
     * Selection of the right symbol and weather description to be displayed
     */
    useEffect(() => {

        // Clear
        if (props.weatherCode === 0) {
            setWeatherDescription("Clear")
            setBackGround("#90caf9")
            setSymbol(<Icons.WiDaySunny size={80} color='yellow' />)
        }
        // Cloudy
        else if (props.weatherCode > 0 && props.weatherCode < 4) {
            setWeatherDescription("Cloudy")
            setBackGround("LightBlue")
            setSymbol(<Icons.WiCloud size={80} color='gray' />)
        }
        // Rainy
        else if (props.weatherCode > 60 && props.weatherCode < 66) {
            setWeatherDescription("Rainy")
            setBackGround("LightGrey")
            setSymbol(<Icons.WiDayRain size={80} color='gray' />)
        }
        // Snowy
        else if (props.weatherCode > 70 && props.weatherCode < 76) {
            setWeatherDescription("Snowy")
            setBackGround("Snow")
            setSymbol(<Icons.WiDaySnow size={80} color='gray' />)
        }
        // Stormy
        else if (props.weatherCode > 94 && props.weatherCode < 100) {
            setWeatherDescription("Stormy")
            setBackGround("LightSteelBlue")
            setSymbol(<Icons.WiDayStormShowers size={80} color='gray' />)
        }
        else console.log("No options left")

    }, [props.weatherCode]);

    return (
        <Card
            pad="small"
            gap="small"
            background={backGround}
            align="center"
        >
            {weatherDescription}
            {symbol}
        </Card>
    )
}