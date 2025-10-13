/*
package com.bookverse.backend.external;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError()
                || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();

        log.error("External API Error - Status: {}, Message: {}",
                statusCode, response.getStatusText());

        switch (statusCode) {
            case NOT_FOUND:
                throw new RuntimeException("Resource not found in external API");
            case UNAUTHORIZED:
                throw new RuntimeException("Unauthorized access to external API");
            case FORBIDDEN:
                throw new RuntimeException("Access forbidden to external API");
            case TOO_MANY_REQUESTS:
                throw new RuntimeException("Rate limit exceeded for external API");
            default:
                throw new RuntimeException("External API error: " + statusCode);
        }
    }
}

 */