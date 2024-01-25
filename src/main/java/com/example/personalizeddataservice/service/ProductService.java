package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(String productId);
}
