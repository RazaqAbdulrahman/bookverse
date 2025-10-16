package com.bookverse.backend.service;

import com.bookverse.backend.dto.ReviewRequest;
import com.bookverse.backend.dto.ReviewResponse;
import com.bookverse.backend.exception.ResourceNotFoundException;
import com.bookverse.backend.exception.UnauthorizedException;
import com.bookverse.backend.model.Review;
import com.bookverse.backend.model.User;
import com.bookverse.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public List<ReviewResponse> getBookReviews(String bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getUserReviews() {
        User user = userService.getCurrentUser();
        List<Review> reviews = reviewRepository.findByUser(user);
        return reviews.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse createReview(ReviewRequest request) {
        User user = userService.getCurrentUser();

        Review review = reviewRepository.findByUserAndBookId(user, request.getBookId())
                .orElse(Review.builder()
                        .user(user)
                        .bookId(request.getBookId())
                        .build());

        review.setRating(request.getRating());
        review.setText(request.getText());

        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Unauthorized to update this review");
        }

        review.setRating(request.getRating());
        review.setText(request.getText());

        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Unauthorized to delete this review");
        }

        reviewRepository.delete(review);
    }

    public Double getAverageRating(String bookId) {
        return reviewRepository.getAverageRatingByBookId(bookId);
    }

    public long getReviewCount(String bookId) {
        return reviewRepository.countByBookId(bookId);
    }

    private ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userEmail(review.getUser().getEmail())
                .bookId(review.getBookId())
                .rating(review.getRating())
                .text(review.getText())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}