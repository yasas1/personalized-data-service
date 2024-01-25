package com.example.personalizeddataservice.domain.dto;

import org.springframework.http.HttpStatus;

public record ResultResponse(HttpStatus status, String message) {
}
