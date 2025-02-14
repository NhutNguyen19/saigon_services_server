package com.iuh.edu.fit.mapper;

import com.iuh.edu.fit.dto.request.UserCreationRequest;
import com.iuh.edu.fit.dto.request.UserUpdateRequest;
import com.iuh.edu.fit.dto.response.UserGetResponse;
import com.iuh.edu.fit.dto.response.UserResponse;
import com.iuh.edu.fit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    UserGetResponse toUserGetResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
