package com.bookverse.backend.service;

import com.bookverse.backend.dto.FavoriteResponse;
import com.bookverse.backend.exception.ResourceAlreadyExistsException;
import com.bookverse.backend.exception.ResourceNotFoundException;
import com.bookverse.backend.model.Favorite;
import com.bookverse.backend.model.User;
import com.bookverse.backend.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    public List<FavoriteResponse> getUserFavorites() {
        User user = userService.getCurrentUser();
        List<Favorite> favorites = favoriteRepository.findByUserOrderByCreatedAtDesc(user);
        return favorites.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public FavoriteResponse addFavorite(String bookId) {
        User user = userService.getCurrentUser();

        if (favoriteRepository.existsByUserAndBookId(user, bookId)) {
            throw new ResourceAlreadyExistsException("Book already in favorites");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .bookId(bookId)
                .build();

        favoriteRepository.save(favorite);
        return mapToResponse(favorite);
    }

    @Transactional
    public void removeFavorite(String bookId) {
        User user = userService.getCurrentUser();

        if (!favoriteRepository.existsByUserAndBookId(user, bookId)) {
            throw new ResourceNotFoundException("Favorite not found");
        }

        favoriteRepository.deleteByUserAndBookId(user, bookId);
    }

    public boolean isFavorite(String bookId) {
        User user = userService.getCurrentUser();
        return favoriteRepository.existsByUserAndBookId(user, bookId);
    }

    private FavoriteResponse mapToResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .bookId(favorite.getBookId())
                .createdAt(favorite.getCreatedAt())
                .build();
    }
}
