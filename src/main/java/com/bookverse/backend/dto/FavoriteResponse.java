package com.bookverse.backend.dto;

import java.time.LocalDateTime;

public class FavoriteResponse {
    private Long id;
    private String bookId;
    private LocalDateTime createdAt;

    public FavoriteResponse() {}

    public FavoriteResponse(Long id, String bookId, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static FavoriteResponseBuilder builder() {
        return new FavoriteResponseBuilder();
    }

    public static class FavoriteResponseBuilder {
        private Long id;
        private String bookId;
        private LocalDateTime createdAt;

        public FavoriteResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FavoriteResponseBuilder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public FavoriteResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FavoriteResponse build() {
            return new FavoriteResponse(id, bookId, createdAt);
        }
    }
}
