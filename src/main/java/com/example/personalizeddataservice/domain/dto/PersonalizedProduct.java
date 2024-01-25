package com.example.personalizeddataservice.domain.dto;

import com.example.personalizeddataservice.domain.model.Product;

public record PersonalizedProduct(Product product, Double relevancyScore){}
