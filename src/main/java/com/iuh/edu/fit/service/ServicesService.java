package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.ServicesDTO;
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
import java.util.Optional;
import java.util.UUID;
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
    public ServicesDTO addService(ServicesDTO serviceDTO) {
        Services service = servicesMapper.toEntity(serviceDTO);

        // ✅ Kiểm tra và lấy Category từ database
        if (serviceDTO.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(serviceDTO.getCategoryId());
            category.ifPresent(service::setCategory);
        }

        // ✅ Kiểm tra và lấy Location từ database
        if (serviceDTO.getLocationId() != null) {
            Optional<Location> location = locationRepository.findById(serviceDTO.getLocationId());
            location.ifPresent(service::setLocation);
        }

        // ✅ Kiểm tra và lấy User từ database
        if (serviceDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(serviceDTO.getUserId());
            user.ifPresent(service::setUser);
        }

        // ✅ Tạo ID nếu chưa có
        if (service.getId() == null || service.getId().trim().isEmpty()) {
            service.setId(UUID.randomUUID().toString());
        }

        Services savedService = servicesRepository.save(service);
        return servicesMapper.toDTO(savedService);
    }

    @Override
    public List<ServicesDTO> getAllServices() {
        return servicesRepository.findAll()
                .stream()
                .map(servicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServicesDTO getServiceById(String serviceId) {
        Services service = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));
        return servicesMapper.toDTO(service);
    }

    @Override
    public ServicesDTO updateService(String serviceId, ServicesDTO serviceDTO) {
        Services existingService = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        existingService.setServiceName(serviceDTO.getServiceName());
        existingService.setDescription(serviceDTO.getDescription());
        existingService.setPrice(serviceDTO.getPrice());

        Services updatedService = servicesRepository.save(existingService);
        return servicesMapper.toDTO(updatedService);
    }

    @Override
    public void deleteService(String serviceId) {
        if (!servicesRepository.existsById(serviceId)) {
            throw new RuntimeException("Service not found with id: " + serviceId);
        }
        servicesRepository.deleteById(serviceId);
    }
}
