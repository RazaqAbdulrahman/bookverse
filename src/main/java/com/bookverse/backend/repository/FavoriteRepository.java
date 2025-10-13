package com.bookverse.backend.repository;

import com.bookverse.backend.model.Favorite;
import com.bookverse.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    List<Favorite> findByUserOrderByCreatedAtDesc(User user);
    Optional<Favorite> findByUserAndBookId(User user, String bookId);
    boolean existsByUserAndBookId(User user, String bookId);
    void deleteByUserAndBookId(User user, String bookId);
}
