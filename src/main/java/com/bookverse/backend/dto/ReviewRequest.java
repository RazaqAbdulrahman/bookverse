package com.bookverse.backend.dto;

public class ReviewRequest {
    private String bookId;
    private int rating;
    private String text;

    public ReviewRequest() {}

    public ReviewRequest(String bookId, int rating, String text) {
        this.bookId = bookId;
        this.rating = rating;
        this.text = text;
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
}
