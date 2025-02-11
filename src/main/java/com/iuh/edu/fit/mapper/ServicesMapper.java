package com.iuh.edu.fit.mapper;

import com.iuh.edu.fit.dto.ServicesDTO;
import com.iuh.edu.fit.model.Services;
import org.springframework.stereotype.Component;

@Component
public class ServicesMapper {
    public Services toEntity(ServicesDTO dto) {
        Services service = new Services();
        service.setId(dto.getId());
        service.setServiceName(dto.getServiceName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        return service;
    }

    public ServicesDTO toDTO(Services service) {
        ServicesDTO dto = new ServicesDTO();
        dto.setId(service.getId());
        dto.setServiceName(service.getServiceName());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());

        // ✅ Đảm bảo không bị `null`
        if (service.getCategory() != null) {
            dto.setCategoryId(service.getCategory().getId());
        }
        if (service.getLocation() != null) {
            dto.setLocationId(service.getLocation().getId());
        }
        if (service.getUser() != null) {
            dto.setUserId(service.getUser().getId());
        }
        return dto;
    }
}
