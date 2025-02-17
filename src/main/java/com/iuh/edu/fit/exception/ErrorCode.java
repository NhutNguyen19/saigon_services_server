package com.iuh.edu.fit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PHONE_USERNAME_EXISTED(1010, "Your phone is invalid", HttpStatus.BAD_REQUEST),
    SERVICE_EXISTED(2001, "Service already exists", HttpStatus.BAD_REQUEST),
    SERVICE_NOT_FOUND(2002, "Service not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(3001, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(3002, "CATEGORY_EXISTED", HttpStatus.BAD_REQUEST),
    LOCATION_NOT_FOUND(4001, "Location not found", HttpStatus.NOT_FOUND),
    REVIEW_EXISTED(5001, "Review already exists", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_FOUND(5002, "Review not found", HttpStatus.NOT_FOUND),
    INVALID_REVIEW_CONTENT(5003, "Invalid review content", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_FOUND(6001, "Image not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
