package com.bookverse.backend.database;

import com.bookverse.backend.repository.BookCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseCleanupService {

    private final BookCacheRepository bookCacheRepository;

    /**
     * Runs every day at 2 AM to delete expired book cache entries older than 7 days
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanExpiredBookCache() {
        log.info("Starting database cleanup - expired book cache");

        LocalDateTime expiryDate = LocalDateTime.now().minusDays(7);

        var expiredCaches = bookCacheRepository.findByCachedAtBefore(expiryDate);

        if (!expiredCaches.isEmpty()) {
            bookCacheRepository.deleteAll(expiredCaches);
            log.info("Deleted {} expired book cache entries", expiredCaches.size());
        } else {
            log.info("No expired book cache entries found");
        }
    }

    /**
     * Logs the current book cache count every hour.
     * Starts 10 seconds after application startup to prevent startup lag.
     * Runs asynchronously so it doesn't block the main thread.
     */
    @Async
    @Scheduled(fixedRate = 3600000, initialDelay = 10000) // 1 hour in milliseconds
    public void logDatabaseStats() {
        long cacheCount = bookCacheRepository.count();
        log.info("Database Stats - Book Cache Entries: {}", cacheCount);
    }
}
