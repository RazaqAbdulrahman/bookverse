package com.bookverse.backend.repository;

import com.bookverse.backend.model.ReadingList;
import com.bookverse.backend.model.User;
import com.bookverse.backend.model.enums.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
    List<ReadingList> findByUser(User user);
    List<ReadingList> findByUserAndStatus(User user, ReadingStatus status);
    Optional<ReadingList> findByUserAndBookId(User user, String bookId);
    boolean existsByUserAndBookId(User user, String bookId);
    void deleteByUserAndBookId(User user, String bookId);
}
