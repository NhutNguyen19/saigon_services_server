package com.iuh.edu.fit.service;

import java.util.List;

import com.iuh.edu.fit.dto.request.FavoriteRequest;
import com.iuh.edu.fit.dto.response.FavoriteResponse;

public interface FavoriteService {
    FavoriteResponse addFavorite(FavoriteRequest request);

    List<FavoriteResponse> getAllFavorites();

    FavoriteResponse getFavoriteById(String favoriteId);

    List<FavoriteResponse> getFavoritesByUser(String userId);

    void deleteFavorite(String favoriteId);
}
