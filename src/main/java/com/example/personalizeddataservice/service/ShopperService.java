package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.ResultResponse;
import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;

public interface ShopperService {
    ShopperDto createShopper(ShopperDto shopperDto);

    ResultResponse saveShopperWithPersonalizedProducts(ShopperPersonalizedProductDto shopperPersonalizedProductDto);
    ShopperDto getShopperById(String shopperId);
}
