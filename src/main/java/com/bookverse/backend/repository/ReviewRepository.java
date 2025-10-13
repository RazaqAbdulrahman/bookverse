package com.bookverse.backend.repository;

import com.bookverse.backend.model.Review;
import com.bookverse.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(String bookId);
    List<Review> findByUser(User user);
    Optional<Review> findByUserAndBookId(User user, String bookId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bookId = :bookId")
    Double getAverageRatingByBookId(@Param("bookId") String bookId);

    long countByBookId(String bookId);
}
