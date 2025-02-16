package com.iuh.edu.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuh.edu.fit.model.Review;

public interface ReviewRepository extends JpaRepository<Review, String> {}
