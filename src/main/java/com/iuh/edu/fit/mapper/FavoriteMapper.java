package com.iuh.edu.fit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.iuh.edu.fit.dto.response.FavoriteResponse;
import com.iuh.edu.fit.model.Favorite;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "serviceName", source = "service.serviceName")
    FavoriteResponse toFavoriteResponse(Favorite favorite);
}
