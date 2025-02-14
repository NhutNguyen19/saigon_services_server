package com.iuh.edu.fit.controller;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserGetResponse;
import com.iuh.edu.fit.dto.response.UserResponse;
import com.iuh.edu.fit.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    ApiResponse<UserGetResponse> register(@Valid @RequestBody UserCreationRequest request){
        return ApiResponse.<UserGetResponse>builder()
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
    ApiResponse<List<UserGetResponse>> getAllUsers(){
        return ApiResponse.<List<UserGetResponse>>builder()
                .data(userService.getAllUsers())
                .message("Get all user")
                .build();
    }

    @GetMapping("/{id}/user")
    ApiResponse<UserResponse> getUserById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .message("Id user :{}" + id)
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<UserResponse> updateUser(
            @RequestBody @Valid UserUpdateRequest request, @PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(request, id))
                .message("Successfully updated user")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<UserResponse> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder()
                .message("Successfully deleted account")
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deleteMyAccount(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteMyAccount(username);
        return ApiResponse.<String>builder()
                .message("Delete my account")
                .build();
    }
}
