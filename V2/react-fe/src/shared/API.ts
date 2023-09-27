import axios from "axios"

/**
 * API communicating with Java backend
 * @param cityName 
 * @returns 
 */
export const CityAPI = (cityName: string) =>{

    const JAVA_BE_PORT  = process.env.REACT_APP_JAVA_BE_PORT
    const baseURL       = `http://localhost:${JAVA_BE_PORT}/city?name=`

    const axiosConfig   ={
        headers: {
            "X-Api-Key": "C1t26Ch1x8ajTwcwiZb2OA==SbUTy8kkFiQ4t2Zd",
        },
        method: 'GET',
        url: `${baseURL}${cityName}`
    }

    console.log("axiosConfig: ", axiosConfig)

    return axios(axiosConfig)
}