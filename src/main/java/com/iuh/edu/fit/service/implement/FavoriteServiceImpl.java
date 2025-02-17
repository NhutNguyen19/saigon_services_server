package com.iuh.edu.fit.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.iuh.edu.fit.dto.request.FavoriteRequest;
import com.iuh.edu.fit.dto.response.FavoriteResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.mapper.FavoriteMapper;
import com.iuh.edu.fit.model.Favorite;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.FavoriteRepository;
import com.iuh.edu.fit.repository.ServicesRepository;
import com.iuh.edu.fit.repository.UserRepository;
import com.iuh.edu.fit.service.FavoriteService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    FavoriteRepository favoriteRepository;
    UserRepository userRepository;
    ServicesRepository servicesRepository;
    FavoriteMapper favoriteMapper;

    @Override
    public FavoriteResponse addFavorite(FavoriteRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Services service = servicesRepository
                .findById(request.getServiceId())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setService(service);

        favoriteRepository.save(favorite);

        return favoriteMapper.toFavoriteResponse(favorite);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<FavoriteResponse> getAllFavorites() {
        return favoriteRepository.findAll().stream()
                .map(favoriteMapper::toFavoriteResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public FavoriteResponse getFavoriteById(String favoriteId) {
        Favorite favorite = favoriteRepository
                .findById(favoriteId)
                .orElseThrow(() -> new AppException(ErrorCode.FAVORITE_NOT_FOUND));
        return favoriteMapper.toFavoriteResponse(favorite);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<FavoriteResponse> getFavoritesByUser(String userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream().map(favoriteMapper::toFavoriteResponse).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteFavorite(String favoriteId) {
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new AppException(ErrorCode.FAVORITE_NOT_FOUND);
        }
        favoriteRepository.deleteById(favoriteId);
    }
}
