package com.example.personalizeddataservice.service.impl;

import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.repository.ProductRepository;
import com.example.personalizeddataservice.service.ProductService;
import com.example.personalizeddataservice.util.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDto productDto) {
        return productRepository.save(ObjectMapper.mapProductDtoToProduct(productDto));
    }

    @Cacheable(value = "products")
    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found for the id " + productId));
    }
}
