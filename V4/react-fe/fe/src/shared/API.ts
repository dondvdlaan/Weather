import axios, { AxiosResponse, Method } from "axios"
import { useEffect, useState } from "react"
import axiosInstance from "./ApiIntercept";
import { currentTime } from "./HelperFunctions";


// General constants
const JAVA_BE_PORT = process.env.REACT_APP_JAVA_BE_PORT
const SAMPLE_FREQ = process.env.REACT_APP_SAMPLE_FREQ || 5


/**
 * API retrieves weather report for city from back end
 * @param cityName 
 * @returns 
 */
export const CityAPI = (_method: string, path: string, cityName: string, data = {}) => {

  const baseURL = `http://localhost:${JAVA_BE_PORT}/`

  const axiosConfig = {
    header: 'Content-Type: application/json',
    method: _method,
    url: `${baseURL}${path}${cityName}`,
    data
  }

  console.log("axios axiosConfig: ", axiosConfig)

  return axios(axiosConfig)
}

type Setter<T> = (data: T) => void;

/*
 * Function customeHook useWeatherApi sends GET requests to back end
 *
 * @param path [string]     : relative path to baseUrl
 * @return                  : Response Data
 */
export function useWeatherApi<T>(path: string): [T | undefined, Setter<T>] {

  const [data, setData] = useState<T>();

  useEffect(() => {

    weatherApi("GET", path, setData);
  }, [path]);

  return [data, setData];
}

/*
 * Function customeHook useSchedulerWeatherApi which, after receiving startTrend signal, 
 * polls backend for cityweather trend data
 *
 * @param startTrend [boolean]    : start signal for scheduler
 * @param path [string]           : relative path to baseUrl
 * @return                        : Response Data
 */
export function useSchedulerWeatherApi<T>(
  startTrend: boolean,
  path: string | undefined): [T | undefined, Setter<T>] {

  const [data, setData] = useState<T>();

  useEffect(() => {

    if (startTrend && path != undefined) {
      //Implementing the setInterval method 
      const interval = setInterval(() => {

        console.log(currentTime(), " Polling back end")
        weatherApi("GET", path, setData);

        // Sample freq in minutes
      }, Number(SAMPLE_FREQ) * 60 * 1000);

      //Clearing the interval 
      return () => clearInterval(interval);
    }
  }, [startTrend]);

  return [data, setData];
}

/*
 * Useful for calls on events or in conditions
 *
 * @param method [Method], http method
 * @param path [string], relative path to baseUrl
 * @param data [function], callback, gets `response.data` as an argument
 * @param data [object], body data
 */
export function weatherApi<T>(
  method: Method,
  path: string,
  callback: Setter<T>,
  data = {}
): void {

  const baseUrl = `http://localhost:${JAVA_BE_PORT}/cityTrend?name=`

  const weatherApiConfig = {
    method: method,
    url: `${baseUrl}${path}`,
    data,
  }

  console.log("API weatherApi path: ", path)
  //console.log(weatherApiConfig)

  axiosInstance.get("cityTrend?name=" + path)
    .then((response: AxiosResponse<T>) => {

      console.log(currentTime(), " response: cityTrend?name=" + path + " ", response)
      return callback(response.data)
    })
    .catch(err => console.log("err: ", err))
  //axios(weatherApiConfig).then((response: AxiosResponse<T>) => callback(response.data));


}