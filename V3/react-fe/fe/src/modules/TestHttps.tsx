import axios from "axios"


export const TestHttps = () => {


    const onClick = () => {

        console.log("In onClick")

        const JAVA_BE_PORT = 8443
        const baseUrl = `https://localhost:${JAVA_BE_PORT}/`
        const path = "test"
        const data = { "cityName": "Duckstad" }

        const weatherApiConfig = {
            method: "POST",
            url: `${baseUrl}${path}`,
            data,
        }

        console.log(weatherApiConfig)

        axios(weatherApiConfig)
            .then(res => console.log("res: " + res))
    }

    return (
        <button
            onClick={onClick }>
            Test HTTPS
        </button>
    )
}