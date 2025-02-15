package com.iuh.edu.fit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuh.edu.fit.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    boolean existsByCategoryName(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);
}
