package com.bookverse.backend.service;

import com.bookverse.backend.dto.ReadingListRequest;
import com.bookverse.backend.dto.ReadingListResponse;
import com.bookverse.backend.exception.ResourceNotFoundException;
import com.bookverse.backend.model.ReadingList;
import com.bookverse.backend.model.User;
import com.bookverse.backend.model.enums.ReadingStatus;
import com.bookverse.backend.repository.ReadingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadingListService {

    private final ReadingListRepository readingListRepository;
    private final UserService userService;

    public List<ReadingListResponse> getUserReadingList() {
        User user = userService.getCurrentUser();
        List<ReadingList> readingList = readingListRepository.findByUser(user);
        return readingList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ReadingListResponse> getByStatus(ReadingStatus status) {
        User user = userService.getCurrentUser();
        List<ReadingList> readingList = readingListRepository.findByUserAndStatus(user, status);
        return readingList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ReadingListResponse addToReadingList(ReadingListRequest request) {
        User user = userService.getCurrentUser();

        ReadingList readingList = readingListRepository
                .findByUserAndBookId(user, request.getBookId())
                .orElse(ReadingList.builder()
                        .user(user)
                        .bookId(request.getBookId())
                        .build());

        readingList.setStatus(request.getStatus());
        readingList.setProgress(request.getProgress());

        readingListRepository.save(readingList);
        return mapToResponse(readingList);
    }

    public ReadingListResponse updateReadingList(String bookId, ReadingListRequest request) {
        User user = userService.getCurrentUser();

        ReadingList readingList = readingListRepository.findByUserAndBookId(user, bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found in reading list"));

        if (request.getStatus() != null) {
            readingList.setStatus(request.getStatus());
        }
        if (request.getProgress() != null) {
            readingList.setProgress(request.getProgress());
        }

        readingListRepository.save(readingList);
        return mapToResponse(readingList);
    }

    @Transactional
    public void removeFromReadingList(String bookId) {
        User user = userService.getCurrentUser();

        if (!readingListRepository.existsByUserAndBookId(user, bookId)) {
            throw new ResourceNotFoundException("Book not found in reading list");
        }

        readingListRepository.deleteByUserAndBookId(user, bookId);
    }

    private ReadingListResponse mapToResponse(ReadingList readingList) {
        return ReadingListResponse.builder()
                .id(readingList.getId())
                .bookId(readingList.getBookId())
                .status(readingList.getStatus())
                .progress(readingList.getProgress())
                .addedAt(readingList.getAddedAt())
                .updatedAt(readingList.getUpdatedAt())
                .build();
    }
}
