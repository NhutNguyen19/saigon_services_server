package com.iuh.edu.fit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.edu.fit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneAndUsername(String phone, String username);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);
}
