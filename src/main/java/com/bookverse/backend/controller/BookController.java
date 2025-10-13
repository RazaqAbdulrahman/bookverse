package com.bookverse.backend.controller;

import com.bookverse.backend.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final GoogleBooksService googleBooksService;

    @GetMapping("/search")
    public ResponseEntity<String> searchBooks(
            @RequestParam String q,
            @RequestParam(required = false, defaultValue = "20") Integer maxResults,
            @RequestParam(required = false, defaultValue = "0") Integer startIndex
    ) {
        String response = googleBooksService.searchBooks(q, maxResults, startIndex);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getBookById(@PathVariable String id) {
        String response = googleBooksService.getBookById(id);
        return ResponseEntity.ok(response);
    }
}
