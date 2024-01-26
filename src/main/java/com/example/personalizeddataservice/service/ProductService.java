package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.domain.model.Product;

public interface ProductService {
    Product createProduct(ProductDto productDto);
    Product getProductById(String productId);
}
