package com.bookverse.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books_cache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookCache {
    @Id
    private String bookId; // Google Books ID is unique

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload; // JSON cache from Google Books API

    @Column(name = "cached_at", nullable = false)
    private LocalDateTime cachedAt;

    @PrePersist
    protected void onCreate() {
        this.cachedAt = LocalDateTime.now();
    }
}
