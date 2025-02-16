package com.iuh.edu.fit.service;

import java.util.List;

import com.iuh.edu.fit.dto.request.ReviewRequest;
import com.iuh.edu.fit.dto.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);

    List<ReviewResponse> getAllReviews();

    ReviewResponse getReviewById(String reviewId);

    ReviewResponse updateReview(String reviewId, ReviewRequest request);

    void deleteReview(String reviewId);
}
