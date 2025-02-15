package com.iuh.edu.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.edu.fit.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {}
