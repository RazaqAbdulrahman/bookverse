package com.bookverse.backend.dto;

import java.time.LocalDateTime;

public class ReviewResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private String bookId;
    private int rating;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponse() {}

    public ReviewResponse(Long id, Long userId, String userEmail, String bookId, int rating,
                          String text, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.bookId = bookId;
        this.rating = rating;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static ReviewResponseBuilder builder() {
        return new ReviewResponseBuilder();
    }

    public static class ReviewResponseBuilder {
        private Long id;
        private Long userId;
        private String userEmail;
        private String bookId;
        private int rating;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ReviewResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ReviewResponseBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public ReviewResponseBuilder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public ReviewResponseBuilder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public ReviewResponseBuilder text(String text) {
            this.text = text;
            return this;
        }

        public ReviewResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ReviewResponse build() {
            return new ReviewResponse(id, userId, userEmail, bookId, rating, text, createdAt, updatedAt);
        }
    }
}
