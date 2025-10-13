package com.bookverse.backend.service;

import com.bookverse.backend.model.BookCache;
import com.bookverse.backend.repository.BookCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final BookCacheRepository bookCacheRepository;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final int CACHE_EXPIRY_DAYS = 7;

    public String searchBooks(String query, Integer maxResults, Integer startIndex) {
        StringBuilder url = new StringBuilder(GOOGLE_BOOKS_API_URL);
        url.append("?q=").append(query);

        if (maxResults != null) {
            url.append("&maxResults=").append(maxResults);
        }

        if (startIndex != null) {
            url.append("&startIndex=").append(startIndex);
        }

        if (apiKey != null && !apiKey.isEmpty()) {
            url.append("&key=").append(apiKey);
        }

        log.info("Searching Google Books API: {}", url);

        try {
            return restTemplate.getForObject(url.toString(), String.class);
        } catch (Exception e) {
            log.error("Error searching books: {}", e.getMessage());
            throw new RuntimeException("Failed to search books: " + e.getMessage());
        }
    }

    public String getBookById(String bookId) {
        Optional<BookCache> cachedBook = bookCacheRepository.findById(bookId);

        if (cachedBook.isPresent()) {
            BookCache cache = cachedBook.get();
            LocalDateTime expiryDate = cache.getCachedAt().plusDays(CACHE_EXPIRY_DAYS);

            if (LocalDateTime.now().isBefore(expiryDate)) {
                log.info("Returning cached book data for bookId: {}", bookId);
                return cache.getPayload();
            } else {
                log.info("Cache expired for bookId: {}", bookId);
                bookCacheRepository.delete(cache);
            }
        }

        String url = GOOGLE_BOOKS_API_URL + "/" + bookId;
        if (apiKey != null && !apiKey.isEmpty()) {
            url += "?key=" + apiKey;
        }

        log.info("Fetching book from Google Books API: {}", url);

        try {
            String response = restTemplate.getForObject(url, String.class);

            BookCache bookCache = BookCache.builder()
                    .bookId(bookId)
                    .payload(response)
                    .build();
            bookCacheRepository.save(bookCache);

            return response;
        } catch (Exception e) {
            log.error("Error fetching book: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch book: " + e.getMessage());
        }
    }

    public void clearExpiredCache() {
        LocalDateTime expiryDate = LocalDateTime.now().minusDays(CACHE_EXPIRY_DAYS);
        bookCacheRepository.findByCachedAtBefore(expiryDate)
                .forEach(bookCacheRepository::delete);
        log.info("Cleared expired book cache");
    }
}

