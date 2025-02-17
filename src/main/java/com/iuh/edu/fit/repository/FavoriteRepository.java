package com.iuh.edu.fit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuh.edu.fit.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    List<Favorite> findByUserId(String userId);
}
