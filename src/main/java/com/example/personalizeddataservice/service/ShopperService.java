package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.domain.model.Shopper;

public interface ShopperService {
    Shopper createShopper(ShopperDto shopperDto);

    void saveShopperWithPersonalizedProducts(ShopperPersonalizedProductDto shopperPersonalizedProductDto);
    Shopper getShopperById(String shopperId);
}
