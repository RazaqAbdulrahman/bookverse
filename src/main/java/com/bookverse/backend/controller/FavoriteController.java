package com.bookverse.backend.controller;

import com.bookverse.backend.dto.ApiResponse;
import com.bookverse.backend.dto.FavoriteResponse;
import com.bookverse.backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getUserFavorites() {
        List<FavoriteResponse> favorites = favoriteService.getUserFavorites();
        return ResponseEntity.ok(ApiResponse.success(favorites));
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(@PathVariable String bookId) {
        FavoriteResponse response = favoriteService.addFavorite(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book added to favorites", response));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(@PathVariable String bookId) {
        favoriteService.removeFavorite(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book removed from favorites", null));
    }

    @GetMapping("/{bookId}/check")
    public ResponseEntity<ApiResponse<Boolean>> checkFavorite(@PathVariable String bookId) {
        boolean isFavorite = favoriteService.isFavorite(bookId);
        return ResponseEntity.ok(ApiResponse.success(isFavorite));
    }
}
