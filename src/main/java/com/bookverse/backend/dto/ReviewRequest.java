package com.bookverse.backend.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class ReviewRequest {

    @NotBlank(message = "Book ID is required")
    private String bookId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @Size(max = 1000, message = "Review text must not exceed 1000 characters")
    private String text;



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
