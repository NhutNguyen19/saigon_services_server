package com.iuh.edu.fit.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.CategoryRequest;
import com.iuh.edu.fit.dto.response.CategoryResponse;
import com.iuh.edu.fit.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/add")
    public ApiResponse<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest request) {
        log.info("Adding new category: {}", request);
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.addCategory(request))
                .message("Category added successfully")
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getAllCategories())
                .message("Fetched all categories")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable String id) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryById(id))
                .message("Fetched category by ID")
                .build();
    }

    @GetMapping("/name/{name}")
    public ApiResponse<CategoryResponse> getCategoryByName(@PathVariable String name) {
        log.info("Fetching category by name: {}", name);
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryByName(name))
                .message("Fetched category by name")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable String id, @Valid @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(id, request))
                .message("Category updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .data("Category deleted successfully")
                .message("Deletion successful")
                .build();
    }
}
