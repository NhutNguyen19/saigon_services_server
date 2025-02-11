package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.mapper.ServicesMapper;
import com.iuh.edu.fit.model.Category;
import com.iuh.edu.fit.model.Location;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.CategoryRepository;
import com.iuh.edu.fit.repository.LocationRepository;
import com.iuh.edu.fit.repository.ServicesRepository;
import com.iuh.edu.fit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesService implements ServicesServiceImpl {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicesMapper servicesMapper;

    @Override
    public ServicesResponse addService(ServicesRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Services service = servicesMapper.toEntity(request, category, location, user);
        Services savedService = servicesRepository.save(service);
        return servicesMapper.toResponse(savedService);
    }

    @Override
    public List<ServicesResponse> getAllServices() {
        return servicesRepository.findAll()
                .stream()
                .map(servicesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServicesResponse getServiceById(String serviceId) {
        Services service = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));
        return servicesMapper.toResponse(service);
    }

    @Override
    public ServicesResponse updateService(String serviceId, ServicesRequest request) {
        Services existingService = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        existingService.setServiceName(request.getServiceName());
        existingService.setDescription(request.getDescription());
        existingService.setPrice(request.getPrice());

        Services updatedService = servicesRepository.save(existingService);
        return servicesMapper.toResponse(updatedService);
    }

    @Override
    public void deleteService(String serviceId) {
        if (!servicesRepository.existsById(serviceId)) {
            throw new RuntimeException("Service not found with id: " + serviceId);
        }
        servicesRepository.deleteById(serviceId);
    }
}
