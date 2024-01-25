package com.example.personalizeddataservice.service;

import com.example.personalizeddataservice.domain.dto.*;

public interface ShopperPersonalizedProductService {

    ResultResponse saveShopperPersonalizedProduct(ShopperPersonalizedProductDto shopperPersonalizedProductDto);

    ResultSetResponse<ProductDto> getPersonalizedProductsByShopperId(String shopperId, String category, String brand, int limit);

    ResultSetResponse<ShopperDto> getShoppersByPersonalizedProductsId(String productId,int limit);
}
