package com.iuh.edu.fit.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.FavoriteRequest;
import com.iuh.edu.fit.dto.response.FavoriteResponse;
import com.iuh.edu.fit.service.FavoriteService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    FavoriteService favoriteService;

    @PostMapping("/add")
    ApiResponse<FavoriteResponse> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        log.info("Adding new favorite: {}", request);
        return ApiResponse.<FavoriteResponse>builder()
                .data(favoriteService.addFavorite(request))
                .message("Favorite added successfully")
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<FavoriteResponse>> getAllFavorites() {
        return ApiResponse.<List<FavoriteResponse>>builder()
                .data(favoriteService.getAllFavorites())
                .message("Fetched all favorites")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<FavoriteResponse> getFavoriteById(@PathVariable String id) {
        return ApiResponse.<FavoriteResponse>builder()
                .data(favoriteService.getFavoriteById(id))
                .message("Fetched favorite by ID")
                .build();
    }

    @GetMapping("/user/{userId}")
    ApiResponse<List<FavoriteResponse>> getFavoritesByUser(@PathVariable String userId) {
        log.info("Fetching favorites for user ID: {}", userId);
        return ApiResponse.<List<FavoriteResponse>>builder()
                .data(favoriteService.getFavoritesByUser(userId))
                .message("Fetched favorites by user ID")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteFavorite(@PathVariable String id) {
        favoriteService.deleteFavorite(id);
        return ApiResponse.<String>builder()
                .data("Favorite deleted successfully")
                .message("Deletion successful")
                .build();
    }
}
