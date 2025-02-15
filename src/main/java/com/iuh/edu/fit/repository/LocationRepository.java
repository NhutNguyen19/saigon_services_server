package com.iuh.edu.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.edu.fit.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {}
