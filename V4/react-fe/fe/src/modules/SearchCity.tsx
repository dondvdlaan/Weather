import { Box, TextInput } from "grommet"
import { useState } from "react";
import { CityAPI } from "../shared/API";
import { CityWeather } from "../types/CityWeather";
import { WeatherDetails } from "./WeatherDetails";
import { Trend } from "./Trend";
import axiosInstance from "../shared/ApiIntercept";
import { useNavigate } from "react-router-dom";
import { Login } from "./login/Login";
import { currentTime } from "../shared/HelperFunctions";

/**
 * This module recieves search inputs from user looking for a city weather report and communicates with
 * backend.
 */
const SearchCity = () => {

  // ---- Constants and variables ----
  const [token, setToken] = useState(localStorage.jwt);
  const [searchItem, setSearchItem] = useState<string | undefined>("")
  const [searchResults, setSearchResults] = useState<CityWeather>();
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [trendTimer, setTrendTimer] = useState<number>()
  const [startTrend, setStartTrend] = useState<boolean>(false)
  const navigate = useNavigate();

  // If login token is not there, go to login
  console.log("token: ", token)
  if (!token) return <Login setToken={setToken} />;

  console.log("DEBUG searchItem: " + searchItem + " " + new Date().toTimeString())

  const defaultCityWeather: CityWeather = {
    name: "",
    country: "noCountry",
    temperature: -270,
    windspeed: -1,
    weathercode: 0,
    is_day: false,
    timezone: "",
    time: new Date(0).toString()
  }

  // Define regex, only characters with or without whitespaces in between
  //let re = /[a-z]/;
  let re: RegExp = /^[a-zA-Z]+\s?[a-zA-Z]*$/;

  const START_TREND_TIMEOUT = process.env.REACT_APP_START_TREND_TIMEOUT || 1

  // ---- Functions ----
  const startTrending = (inputValue: any) => {

    console.log("searchItem before if : ", searchItem, " " + new Date().toTimeString())

    // Start BE trending
    if (searchItem) {
      console.log("inputValue after if: ", inputValue, " " + new Date().toTimeString())

      axiosInstance.post("/startTrend", { "cityName": inputValue })
        //CityAPI("POST", "startTrend", "", { "cityName": inputValue })
        .then(res => {
          console.log("startTrend: ", res.data)
        })
        .catch(err => {
          console.log("err: ", err)
          setErrorMessage("Out of order. Pls try again later.")

          /**
          * Check if token in storage is empty and error is 403 forbidden, 
          * then refresh page, which will lead to login page
          */
          if (localStorage.getItem("jwt") == null && err.response.status == 403) {
            console.log("onTest catch in if");
            navigate(0)
          }
        })
    }

    console.log("Starting FE trending ")
    setStartTrend(true)
  }

  /**
   * Function to communicate with back end and in case of failure, error message is sent to user
   * 
   * @return  setSearchResults(res.data)  : City weather report
   * @error   setErrorMessage             : Error message displayed to user
   */
  const communicateToBackEnd = (method: string, path: string, inputValue: string, data = {}) => {

    // Retrieve weather report for city from back end
    axiosInstance.get(path + inputValue)
      // CityAPI(method, path, inputValue, data)
      .then(res => {
        console.log("cityWeather: ", res.data)
        setSearchResults(res.data)
      })
      .catch(err => {
        console.log("err: ", err)
        setSearchResults(defaultCityWeather)
        setErrorMessage("Out of order. Pls try again later.")

        /**
        * Check if token in storage is empty and error is 403 forbidden, 
        * then refresh page, which will lead to login page
        */
        if (localStorage.getItem("jwt") == null && err.response.status == 403) {
          console.log("onTest catch in if");
          navigate(0)
        }
      })
  }



  // ---- Events ----
  /**
   * Function checks change at input field and calls back end for weather on required city
   * @param c : filed input value
   */
  const onChange = (c: React.ChangeEvent<HTMLInputElement>) => {

    // Reset error message
    setErrorMessage("")

    // Reset trendTimer
    clearTimeout(trendTimer)
    console.log("Cleared timer: " + trendTimer)

    const inputValue = c.target.value;
    console.log("inputValue: ", inputValue)
    setSearchItem(() => inputValue)

    // Minimum input length required and check Regex
    if ((inputValue.length > 2) && re.test(inputValue)) {

      communicateToBackEnd("GET", "city", `?name=${inputValue}`)

      // Start timer before starting trending at back end
      let _trendTimer = setTimeout(
        () => startTrending(inputValue),
        Number(START_TREND_TIMEOUT) * 60 * 1000, inputValue)

      setTrendTimer(_trendTimer)
      console.log("Started trend timer: " + _trendTimer)
      console.log("Started trend timer, searchItem: " + searchItem + " " + currentTime())
    }
  }

  /**
   * Function gives possibility to click on suggested city which comes back from back end.
   * When clicked up on, value is copied in to input field
   */
  const onClick = () => {

    console.log("clickValue: ", searchResults?.name)

    setSearchItem(searchResults?.name)
  }

  return (
    <>
      <Box fill align="center" justify="start" pad="large">
        <Box width="medium">

          <TextInput
            placeholder="type here"
            id="text-input"
            value={searchItem}
            onChange={c => onChange(c)}
          />
          <span
            onClick={onClick}>
            <p> City: {searchResults?.name} </p>
          </span>
          <span><p> {errorMessage} </p></span>
        </Box>
      </Box>
      <WeatherDetails cityWeather={searchResults} />
      <Trend city={searchItem} startTrend={startTrend} />

    </>
  )
}

export default SearchCity;