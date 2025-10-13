/*
package com.bookverse.backend.external;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
                                     RestTemplateErrorHandler errorHandler) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .errorHandler(errorHandler)
                .interceptors(loggingInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            // Log request details if needed
            return execution.execute(request, body);
        };
    }
}

 */