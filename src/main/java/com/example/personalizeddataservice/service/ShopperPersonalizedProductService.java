package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import org.springframework.data.domain.Page;

public interface ShopperPersonalizedProductService {

    void saveShopperPersonalizedProduct(ShopperPersonalizedProductDto shopperPersonalizedProductDto);

    Page<Product> getPersonalizedProductsByShopperId(String shopperId, String category, String brand, int limit);

    Page<Shopper> getShoppersByPersonalizedProductsId(String productId, int limit);
}
