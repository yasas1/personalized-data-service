package com.example.personalizeddataservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {
    @NotNull(message = "Product Id cannot be null")
    @NotEmpty(message = "Product Id cannot be empty")
    private String productId;
    @NotNull(message = "Category cannot be null")
    @NotEmpty(message = "Category cannot be empty")
    private String category;
    @NotNull(message = "Brand cannot be null")
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;
}
