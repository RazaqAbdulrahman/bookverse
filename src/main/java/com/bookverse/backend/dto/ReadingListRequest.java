package com.bookverse.backend.dto;

import com.bookverse.backend.model.enums.ReadingStatus;

public class ReadingListRequest {
    private String bookId;
    private ReadingStatus status;
    private Integer progress;

    public ReadingListRequest() {}

    public ReadingListRequest(String bookId, ReadingStatus status, Integer progress) {
        this.bookId = bookId;
        this.status = status;
        this.progress = progress;
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
}
