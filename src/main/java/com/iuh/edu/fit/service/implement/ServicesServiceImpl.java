package com.iuh.edu.fit.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.mapper.ServicesMapper;
import com.iuh.edu.fit.model.Category;
import com.iuh.edu.fit.model.Location;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.CategoryRepository;
import com.iuh.edu.fit.repository.LocationRepository;
import com.iuh.edu.fit.repository.ServicesRepository;
import com.iuh.edu.fit.repository.UserRepository;
import com.iuh.edu.fit.service.ServicesService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ServicesServiceImpl implements ServicesService {

    ServicesRepository servicesRepository;
    CategoryRepository categoryRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ServicesMapper servicesMapper;

    @Override
    public ServicesResponse addService(ServicesRequest request) {
        if (servicesRepository.existsByServiceName(request.getServiceName())) {
            throw new AppException(ErrorCode.SERVICE_EXISTED);
        }
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Location location = locationRepository
                .findById(request.getLocationId())
                .orElseThrow(() -> new AppException(ErrorCode.LOCATION_NOT_FOUND));

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Services service = servicesMapper.toService(request);

        service.setCategory(category);
        service.setLocation(location);
        service.setUser(user);
        servicesRepository.save(service);
        return servicesMapper.toServiceResponse(service);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ServicesResponse> getAllServices() {
        return servicesRepository.findAll().stream()
                .map(servicesMapper::toServiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ServicesResponse getServiceById(String serviceId) {
        Services service =
                servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        return servicesMapper.toServiceResponse(service);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ServicesResponse getServiceByName(String serviceName) {
        Services service = servicesRepository
                .findByServiceName(serviceName)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        return servicesMapper.toServiceResponse(service);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ServicesResponse updateService(String serviceId, ServicesRequest request) {
        Services existingService =
                servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        servicesMapper.updateService(existingService, request);
        servicesRepository.save(existingService);

        return servicesMapper.toServiceResponse(existingService);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteService(String serviceId) {
        if (!servicesRepository.existsById(serviceId)) {
            throw new AppException(ErrorCode.SERVICE_NOT_FOUND);
        }
        servicesRepository.deleteById(serviceId);
    }
}
