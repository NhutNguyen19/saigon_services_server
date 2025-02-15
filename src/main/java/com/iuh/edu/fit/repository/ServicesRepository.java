package com.iuh.edu.fit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuh.edu.fit.model.Services;

public interface ServicesRepository extends JpaRepository<Services, String> {

    boolean existsByServiceName(String serviceName);

    Optional<Services> findByServiceName(String serviceName);
}
