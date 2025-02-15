package com.iuh.edu.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.edu.fit.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
