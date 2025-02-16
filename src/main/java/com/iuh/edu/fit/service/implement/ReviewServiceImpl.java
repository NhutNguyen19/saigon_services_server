package com.iuh.edu.fit.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.iuh.edu.fit.dto.request.ReviewRequest;
import com.iuh.edu.fit.dto.response.ReviewResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.mapper.ReviewMapper;
import com.iuh.edu.fit.model.Review;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.ReviewRepository;
import com.iuh.edu.fit.repository.ServicesRepository;
import com.iuh.edu.fit.repository.UserRepository;
import com.iuh.edu.fit.service.ReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    UserRepository userRepository;
    ServicesRepository servicesRepository;
    ReviewMapper reviewMapper;

    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Services service = servicesRepository
                .findById(request.getServiceId())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        Review review = reviewMapper.toReview(request);
        review.setUser(user);
        review.setService(service);

        reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ReviewResponse getReviewById(String reviewId) {
        Review review =
                reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ReviewResponse updateReview(String reviewId, ReviewRequest request) {
        Review existingReview =
                reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        reviewMapper.updateReview(existingReview, request);
        reviewRepository.save(existingReview);
        return reviewMapper.toReviewResponse(existingReview);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteReview(String reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
    }
}
