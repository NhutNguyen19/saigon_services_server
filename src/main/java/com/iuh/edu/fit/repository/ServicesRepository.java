package com.iuh.edu.fit.repository;

import com.iuh.edu.fit.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, String> { // Đổi id thành String
}
