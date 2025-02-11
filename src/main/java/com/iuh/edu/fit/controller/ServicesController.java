package com.iuh.edu.fit.controller;

import com.iuh.edu.fit.dto.ServicesDTO;
import com.iuh.edu.fit.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

    @Autowired
    private ServicesService serviceService;

    @PostMapping
    public ResponseEntity<ServicesDTO> addService(@RequestBody ServicesDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.addService(serviceDTO));
    }

    @GetMapping
    public ResponseEntity<List<ServicesDTO>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicesDTO> getServiceById(@PathVariable String id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicesDTO> updateService(@PathVariable String id, @RequestBody ServicesDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok("Service deleted successfully");
    }
}
