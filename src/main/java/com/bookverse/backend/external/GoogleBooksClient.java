package com.bookverse.backend.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksClient {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    public String searchBooks(String query, Integer maxResults, Integer startIndex) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_BASE_URL)
                .queryParam("q", query);

        if (maxResults != null) {
            builder.queryParam("maxResults", maxResults);
        }

        if (startIndex != null) {
            builder.queryParam("startIndex", startIndex);
        }

        if (apiKey != null && !apiKey.isEmpty()) {
            builder.queryParam("key", apiKey);
        }

        String url = builder.toUriString();
        log.info("Calling Google Books API: {}", url);

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            log.error("Error calling Google Books API: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch books from Google Books API", e);
        }
    }

    public String getBookById(String bookId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(GOOGLE_BOOKS_BASE_URL + "/" + bookId);

        if (apiKey != null && !apiKey.isEmpty()) {
            builder.queryParam("key", apiKey);
        }

        String url = builder.toUriString();
        log.info("Calling Google Books API for book: {}", bookId);

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            log.error("Error fetching book from Google Books API: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch book with ID: " + bookId, e);
        }
    }
}