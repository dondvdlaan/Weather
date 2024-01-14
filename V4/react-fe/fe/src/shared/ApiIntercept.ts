import axios from "axios";

/**
 * Create axios instance
 */

const instance = axios.create({
    baseURL: "http://localhost:8080/",
    headers: {
        "Content-Type": "application/json",
        withCredentials: true
    },
});

/**
 * Axios instance outgoing request configuration
 */
instance.interceptors.request.use(

    (config) => {
        // Retrieve token from storage
        console.log("interceptors.request Loading jwt")
        const token = localStorage.getItem("jwt");

        // Compose header
        if (token) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        console.log("interceptors.request config:", config)
        // Return modified config to request
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

/**
 * Axios instance response configuration
 */
instance.interceptors.response.use(
    (res) => {
        return res;
    },
    async (err: any) => {

        // Remember origial config
        const originalConfig = err.config;

        // Debug
        console.log("in instance.interceptors.response.use err ", originalConfig)

        if (originalConfig.url !== "/login" && err.response) {

            console.log("in if url!= login")
            console.log("originalConfig._retryJwt", originalConfig._retryJwt)

            // Check if Token expired
            if (err.response.status === 401 && !originalConfig._retryJwt) {

                console.log("in err.response.status === 401")

                // Remember first 401 token expired error to avoid looping between fe and be
                originalConfig._retryJwt = true;

                // Clear token from local storage
                localStorage.removeItem("jwt")

                try {

                    // Request new token by way of refreshToken
                    const rs = await instance.post("refreshtoken", {
                        refreshToken: localStorage.getItem("refreshToken"),
                    });

                    console.log("rs.data: ", rs.data)

                    // Store renewed token
                    const token = rs.data.jwtToken;
                    localStorage.setItem("jwt", token);

                    // Store renewed refresh token
                    const refreshToken = rs.data.refreshToken;
                    localStorage.setItem("refreshToken", refreshToken);

                    // Continue with original config in the response
                    return instance(originalConfig);

                } catch (_error) {
                    console.log("Refresh token failed")
                    localStorage.removeItem("refreshToken")
                    localStorage.removeItem("jwt")

                    return Promise.reject(_error);
                }
            }
        }
        return Promise.reject(err);
    }

);

export default instance;