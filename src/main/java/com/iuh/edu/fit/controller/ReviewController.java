package com.iuh.edu.fit.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.ReviewRequest;
import com.iuh.edu.fit.dto.response.ReviewResponse;
import com.iuh.edu.fit.service.ReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reviews")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    ReviewService reviewService;

    @PostMapping("/add")
    ApiResponse<ReviewResponse> addReview(@Valid @RequestBody ReviewRequest request) {
        log.info("Adding new review: {}", request);
        return ApiResponse.<ReviewResponse>builder()
                .data(reviewService.addReview(request))
                .message("Review added successfully")
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<ReviewResponse>> getAllReviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .data(reviewService.getAllReviews())
                .message("Fetched all reviews")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ReviewResponse> getReviewById(@PathVariable String id) {
        return ApiResponse.<ReviewResponse>builder()
                .data(reviewService.getReviewById(id))
                .message("Fetched review by ID")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ReviewResponse> updateReview(@PathVariable String id, @Valid @RequestBody ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .data(reviewService.updateReview(id, request))
                .message("Review updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ApiResponse.<String>builder()
                .data("Review deleted successfully")
                .message("Deletion successful")
                .build();
    }
}
