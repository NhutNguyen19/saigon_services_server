package com.iuh.edu.fit.controller;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.response.UserResponse;
import com.iuh.edu.fit.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    ApiResponse<UserResponse> register(@Valid @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .message("Register successfully")
                .build();
    }

    @GetMapping("/info")
    ApiResponse<UserResponse> myInfo(){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .message("My Info")
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
                .message("Get all user")
                .build();
    }
}
