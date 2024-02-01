package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.BulkProductDto;
import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.domain.model.Product;

public interface ProductService {
    Product createProduct(ProductDto productDto);

    void createBulkProduct(BulkProductDto bulkProductDto);
    Product getProductById(String productId);
}
