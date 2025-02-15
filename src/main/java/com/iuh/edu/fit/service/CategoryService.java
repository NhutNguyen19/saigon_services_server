package com.iuh.edu.fit.service;

import java.util.List;

import com.iuh.edu.fit.dto.request.CategoryRequest;
import com.iuh.edu.fit.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String categoryId);

    CategoryResponse getCategoryByName(String categoryName);

    CategoryResponse updateCategory(String categoryId, CategoryRequest request);

    void deleteCategory(String categoryId);
}
