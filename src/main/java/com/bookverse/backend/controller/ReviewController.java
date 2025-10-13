package com.bookverse.backend.controller;

import com.bookverse.backend.dto.ApiResponse;
import com.bookverse.backend.dto.ReviewRequest;
import com.bookverse.backend.dto.ReviewResponse;
import com.bookverse.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getBookReviews(@PathVariable String bookId) {
        List<ReviewResponse> reviews = reviewService.getBookReviews(bookId);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @GetMapping("/book/{bookId}/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBookReviewStats(@PathVariable String bookId) {
        Double averageRating = reviewService.getAverageRating(bookId);
        long reviewCount = reviewService.getReviewCount(bookId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", averageRating != null ? averageRating : 0.0);
        stats.put("reviewCount", reviewCount);

        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getUserReviews() {
        List<ReviewResponse> reviews = reviewService.getUserReviews();
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(ApiResponse.success("Review created successfully", response));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(ApiResponse.success("Review updated successfully", response));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully", null));
    }
}