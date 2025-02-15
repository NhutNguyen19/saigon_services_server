package com.iuh.edu.fit.service;

import java.util.List;

import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserGetResponse;
import com.iuh.edu.fit.dto.response.UserResponse;

public interface UserService {
    UserGetResponse createUser(UserCreationRequest request);

    List<UserGetResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse updateUser(UserUpdateRequest request, String id);

    void deleteUser(String id);

    UserResponse getMyInfo();
}
