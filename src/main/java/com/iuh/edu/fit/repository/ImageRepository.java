package com.iuh.edu.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuh.edu.fit.model.Image;

public interface ImageRepository extends JpaRepository<Image, String> {}
