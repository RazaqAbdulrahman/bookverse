package com.bookverse.backend.repository;

import com.bookverse.backend.model.BookCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookCacheRepository extends JpaRepository<BookCache, String> {
    List<BookCache> findByCachedAtBefore(LocalDateTime dateTime);
}