package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse updateUser(UserUpdateRequest request, String id);

    void deleteUser(String id);

    UserResponse getMyInfo();
}
