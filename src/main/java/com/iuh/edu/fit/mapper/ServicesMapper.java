package com.iuh.edu.fit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.model.Services;

@Mapper(componentModel = "spring")
public interface ServicesMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "user", ignore = true)
    Services toService(ServicesRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "userId", source = "user.id")
    ServicesResponse toServiceResponse(Services service);

    void updateService(@MappingTarget Services service, ServicesRequest request);
}
