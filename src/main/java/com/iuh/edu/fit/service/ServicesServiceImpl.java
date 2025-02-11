package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.ServicesDTO;
import java.util.List;

public interface ServicesServiceImpl {
    ServicesDTO addService(ServicesDTO serviceDTO);
    List<ServicesDTO> getAllServices();
    ServicesDTO getServiceById(String serviceId);
    ServicesDTO updateService(String serviceId, ServicesDTO serviceDTO);
    void deleteService(String serviceId);
}
