package com.iuh.edu.fit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.iuh.edu.fit.dto.request.CategoryRequest;
import com.iuh.edu.fit.dto.response.CategoryResponse;
import com.iuh.edu.fit.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
