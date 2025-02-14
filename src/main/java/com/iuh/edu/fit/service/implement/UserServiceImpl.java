package com.iuh.edu.fit.service.implement;

import com.iuh.edu.fit.constant.PredefinedRole;
import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.mapper.UserMapper;
import com.iuh.edu.fit.model.Role;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.RoleRepository;
import com.iuh.edu.fit.repository.UserRepository;
import com.iuh.edu.fit.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByPhone(request.getPhone())) throw new AppException(ErrorCode.PHONE_USERNAME_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication.getName());
        log.info("Authorities: {}", authentication.getAuthorities());

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(String id) {
        return null;
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, String id) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

}
