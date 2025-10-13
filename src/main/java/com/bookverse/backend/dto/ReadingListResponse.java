package com.bookverse.backend.dto;

import com.bookverse.backend.model.enums.ReadingStatus;
import java.time.LocalDateTime;

public class ReadingListResponse {
    private Long id;
    private String bookId;
    private ReadingStatus status;
    private Integer progress;
    private LocalDateTime addedAt;
    private LocalDateTime updatedAt;

    public ReadingListResponse() {}

    public ReadingListResponse(Long id, String bookId, ReadingStatus status, Integer progress,
                               LocalDateTime addedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bookId = bookId;
        this.status = status;
        this.progress = progress;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
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

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static ReadingListResponseBuilder builder() {
        return new ReadingListResponseBuilder();
    }

    public static class ReadingListResponseBuilder {
        private Long id;
        private String bookId;
        private ReadingStatus status;
        private Integer progress;
        private LocalDateTime addedAt;
        private LocalDateTime updatedAt;

        public ReadingListResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReadingListResponseBuilder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public ReadingListResponseBuilder status(ReadingStatus status) {
            this.status = status;
            return this;
        }

        public ReadingListResponseBuilder progress(Integer progress) {
            this.progress = progress;
            return this;
        }

        public ReadingListResponseBuilder addedAt(LocalDateTime addedAt) {
            this.addedAt = addedAt;
            return this;
        }

        public ReadingListResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ReadingListResponse build() {
            return new ReadingListResponse(id, bookId, status, progress, addedAt, updatedAt);
        }
    }
}