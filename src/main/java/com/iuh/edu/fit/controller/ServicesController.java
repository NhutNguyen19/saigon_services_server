package com.iuh.edu.fit.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.service.ServicesService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/services")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ServicesController {

    ServicesService serviceService;

    @PostMapping("/add")
    ApiResponse<ServicesResponse> addService(@Valid @RequestBody ServicesRequest request) {
        log.info("Adding new service: {}", request);
        return ApiResponse.<ServicesResponse>builder()
                .data(serviceService.addService(request))
                .message("Service added successfully")
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<ServicesResponse>> getAllServices() {
        return ApiResponse.<List<ServicesResponse>>builder()
                .data(serviceService.getAllServices())
                .message("Fetched all services")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ServicesResponse> getServiceById(@PathVariable String id) {
        return ApiResponse.<ServicesResponse>builder()
                .data(serviceService.getServiceById(id))
                .message("Fetched service by ID")
                .build();
    }

    @GetMapping("/name/{serviceName}")
    ApiResponse<ServicesResponse> getServiceByName(@PathVariable String serviceName) {
        log.info("Fetching service by name: {}", serviceName);
        return ApiResponse.<ServicesResponse>builder()
                .data(serviceService.getServiceByName(serviceName))
                .message("Fetched service by name")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ServicesResponse> updateService(@PathVariable String id, @Valid @RequestBody ServicesRequest request) {
        return ApiResponse.<ServicesResponse>builder()
                .data(serviceService.updateService(id, request))
                .message("Service updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ApiResponse.<String>builder()
                .data("Service deleted successfully")
                .message("Deletion successful")
                .build();
    }
}
