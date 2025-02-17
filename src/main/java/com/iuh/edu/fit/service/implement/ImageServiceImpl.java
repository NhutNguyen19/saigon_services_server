package com.iuh.edu.fit.service.implement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iuh.edu.fit.dto.response.ImageResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.mapper.ImageMapper;
import com.iuh.edu.fit.model.Image;
import com.iuh.edu.fit.model.Services;
import com.iuh.edu.fit.repository.ImageRepository;
import com.iuh.edu.fit.repository.ServicesRepository;
import com.iuh.edu.fit.service.ImageService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageServiceImpl implements ImageService {

    ImageRepository imageRepository;
    ServicesRepository servicesRepository;
    ImageMapper imageMapper;
    String uploadDir;

    public ImageServiceImpl(
            ImageRepository imageRepository,
            ServicesRepository servicesRepository,
            ImageMapper imageMapper,
            @Value("${app.image.upload-dir:images}") String uploadDir) {
        this.imageRepository = imageRepository;
        this.servicesRepository = servicesRepository;
        this.imageMapper = imageMapper;
        this.uploadDir = uploadDir;
    }

    @Override
    public ImageResponse uploadImage(MultipartFile file, String serviceId) {
        // Tìm dịch vụ theo serviceId với cấu trúc yêu cầu
        Services service =
                servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        // Xác định thư mục lưu trữ
        String uploadDir = "C:\\Users\\Admin\\AppData\\Local\\Temp\\images";
        File directory = new File(uploadDir);


        if (!directory.exists()) {
            directory.mkdirs();
        }

        
        String fileName = serviceId + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        try {
           
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        
        Image image = new Image();
        image.setImageUrl(filePath);
        image.setService(service);
        imageRepository.save(image);

        
        return imageMapper.toImageResponse(image);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(imageMapper::toImageResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ImageResponse getImageById(String id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));
        return imageMapper.toImageResponse(image);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));
        File file = new File(image.getImageUrl());
        if (file.exists()) {
            file.delete();
        }
        imageRepository.deleteById(id);
    }
}
