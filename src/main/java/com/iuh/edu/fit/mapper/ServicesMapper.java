package com.iuh.edu.fit.mapper;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.model.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicesMapper {
    
    Services toService(ServicesRequest request);

    ServicesResponse toServiceResponse(Services service);

    
    void updateService(@MappingTarget Services service, ServicesRequest request);
}
