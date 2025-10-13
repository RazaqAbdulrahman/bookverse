package com.bookverse.backend.controller;

import com.bookverse.backend.dto.ApiResponse;
import com.bookverse.backend.dto.ReadingListRequest;
import com.bookverse.backend.dto.ReadingListResponse;
import com.bookverse.backend.model.enums.ReadingStatus;
import com.bookverse.backend.service.ReadingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reading-list")
@RequiredArgsConstructor
public class ReadingListController {

    private final ReadingListService readingListService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadingListResponse>>> getReadingList() {
        List<ReadingListResponse> readingList = readingListService.getUserReadingList();
        return ResponseEntity.ok(ApiResponse.success(readingList));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ReadingListResponse>>> getByStatus(@PathVariable ReadingStatus status) {
        List<ReadingListResponse> readingList = readingListService.getByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(readingList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReadingListResponse>> addToReadingList(@RequestBody ReadingListRequest request) {
        ReadingListResponse response = readingListService.addToReadingList(request);
        return ResponseEntity.ok(ApiResponse.success("Book added to reading list", response));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<ReadingListResponse>> updateReadingList(
            @PathVariable String bookId,
            @RequestBody ReadingListRequest request
    ) {
        ReadingListResponse response = readingListService.updateReadingList(bookId, request);
        return ResponseEntity.ok(ApiResponse.success("Reading list updated", response));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> removeFromReadingList(@PathVariable String bookId) {
        readingListService.removeFromReadingList(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book removed from reading list", null));
    }
}