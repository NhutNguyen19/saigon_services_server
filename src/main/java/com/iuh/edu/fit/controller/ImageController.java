package com.iuh.edu.fit.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.iuh.edu.fit.dto.ApiResponse;
import com.iuh.edu.fit.dto.response.ImageResponse;
import com.iuh.edu.fit.service.ImageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/images")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file, @RequestParam("serviceId") String serviceId) {
        log.info("Uploading image for serviceId: {}", serviceId);
        return ApiResponse.<ImageResponse>builder()
                .data(imageService.uploadImage(file, serviceId))
                .message("Image uploaded successfully")
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<ImageResponse>> getAllImages() {
        return ApiResponse.<List<ImageResponse>>builder()
                .data(imageService.getAllImages())
                .message("Fetched all images")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ImageResponse> getImageById(@PathVariable String id) {
        return ApiResponse.<ImageResponse>builder()
                .data(imageService.getImageById(id))
                .message("Fetched image by ID")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteImage(@PathVariable String id) {
        imageService.deleteImage(id);
        return ApiResponse.<String>builder()
                .data("Image deleted successfully")
                .message("Deletion successful")
                .build();
    }
}
