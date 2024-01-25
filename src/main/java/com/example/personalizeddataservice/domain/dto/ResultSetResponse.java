package com.example.personalizeddataservice.domain.dto;

import java.util.List;


public record ResultSetResponse<T>(int limit, long totalElement, int totalPages, List<T> content) { }
