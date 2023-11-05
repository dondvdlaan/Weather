import axios, { AxiosResponse, Method } from "axios"
import { useEffect, useState } from "react"

// General constants
const JAVA_BE_PORT = process.env.REACT_APP_JAVA_BE_PORT
const SAMPLE_FREQ= process.env.REACT_APP_SAMPLE_FREQ || 5


/**
 * API retrieves weather report for city from back end
 * @param cityName 
 * @returns 
 */
export const CityAPI = (_method: string, path:string, cityName: string, data={}) => {

  const baseURL = `http://localhost:${JAVA_BE_PORT}/`

  const axiosConfig = {
    header: 'Content-Type: application/json',
    method: _method,
    url: `${baseURL}${path}${cityName}`,
    data
  }

  console.log("axiosConfig: ", axiosConfig)

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
 * polls back end for cityweather trend data
 *
 * @param startTrend [boolean]    : start signal for scheduler
 * @param path [string]           : relative path to baseUrl
 * @return                        : Response Data
 */
export function useSchedulerWeatherApi<T>(startTrend: boolean, path: string | undefined): [T | undefined, Setter<T>] {

  const [data, setData] = useState<T>();

  useEffect(() => {

    if (startTrend && path != undefined) {
      //Implementing the setInterval method 
      const interval = setInterval(() => {

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

  axios({
    method: method,
    url: `${baseUrl}/${path}`,
    data,
  }).then((response: AxiosResponse<T>) => callback(response.data));
}