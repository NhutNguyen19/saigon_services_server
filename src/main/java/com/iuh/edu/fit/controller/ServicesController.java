package com.iuh.edu.fit.controller;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import com.iuh.edu.fit.service.ServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/services")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ServicesController {

     ServicesService serviceService;

    @PostMapping
     ApiResponse<ServicesResponse> addService(@Valid @RequestBody ServicesRequest request) {
        log.info("Adding new service: {}", request);
        return ApiResponse.<ServicesResponse>builder()
                .data(serviceService.addService(request))
                .message("Service added successfully")
                .build();
    }

    @GetMapping
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

    @PutMapping("/{id}")
    ApiResponse<ServicesResponse> updateService(
            @PathVariable String id,
            @Valid @RequestBody ServicesRequest request) {
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
