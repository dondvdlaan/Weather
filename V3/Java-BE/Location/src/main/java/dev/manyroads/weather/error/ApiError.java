package dev.manyroads.weather.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model error record for the conversion from JSON error message to POJO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiError(String timestamp,
                       String path,
                       int status,
                       String error,
                       String requestId
                       ) {
    /*
    "{"timestamp":"2023-10-02T12:44:23.555+00:00","path":"/city","status":500,"error":"Internal Server Erro
    r","requestId":"6a7e6387-8"}

    {"message": "Internal server error"}
     */
}
