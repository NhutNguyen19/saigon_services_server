package com.iuh.edu.fit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.iuh.edu.fit.dto.request.ReviewRequest;
import com.iuh.edu.fit.dto.response.ReviewResponse;
import com.iuh.edu.fit.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "service", ignore = true)
    Review toReview(ReviewRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "serviceId", source = "service.id")
    ReviewResponse toReviewResponse(Review review);

    void updateReview(@MappingTarget Review review, ReviewRequest request);
}
