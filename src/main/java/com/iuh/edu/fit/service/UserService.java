package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserGetResponse;
import com.iuh.edu.fit.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserGetResponse createUser(UserCreationRequest request);

    List<UserGetResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse updateUser(UserUpdateRequest request, String id);

    void deleteUser(String id);

    void deleteMyAccount(String username);

    UserResponse updateMyAccount(UserUpdateRequest request);

    UserResponse getMyInfo();
}
