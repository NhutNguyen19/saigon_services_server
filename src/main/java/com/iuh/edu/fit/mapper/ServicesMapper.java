package com.iuh.edu.fit.mapper;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.model.Category;
import com.iuh.edu.fit.model.Location;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.model.User;
import org.springframework.stereotype.Component;

@Component
public class ServicesMapper {

    public Services toEntity(ServicesRequest dto, Category category, Location location, User user) {
        Services service = new Services();
        service.setServiceName(dto.getServiceName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setCategory(category);
        service.setLocation(location);
        service.setUser(user);
        return service;
    }

    
    public ServicesResponse toResponse(Services service) {
        ServicesResponse response = new ServicesResponse();
        response.setId(service.getId());
        response.setServiceName(service.getServiceName());
        response.setDescription(service.getDescription());
        response.setPrice(service.getPrice());

        if (service.getCategory() != null) {
            response.setCategoryId(service.getCategory().getId());
        }
        if (service.getLocation() != null) {
            response.setLocationId(service.getLocation().getId());
        }
        if (service.getUser() != null) {
            response.setUserId(service.getUser().getId());
        }
        return response;
    }
}
