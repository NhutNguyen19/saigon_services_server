package com.iuh.edu.fit.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.response.UserGetResponse;
import com.iuh.edu.fit.dto.response.UserResponse;
import com.iuh.edu.fit.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    ApiResponse<UserGetResponse> register(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<UserGetResponse>builder()
                .data(userService.createUser(request))
                .message("Register successfully")
                .build();
    }

    @GetMapping("/info")
    ApiResponse<UserResponse> myInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .message("My Info")
                .build();
    }

    @GetMapping
    ApiResponse<List<UserGetResponse>> getAllUsers() {
        return ApiResponse.<List<UserGetResponse>>builder()
                .data(userService.getAllUsers())
                .message("Get all user")
                .build();
    }
}
