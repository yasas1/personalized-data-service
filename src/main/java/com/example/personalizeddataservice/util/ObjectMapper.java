package com.example.personalizeddataservice.util;

import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;

public class ObjectMapper {
    private ObjectMapper() {
    }

    public static Product mapProductDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.getProductId())
                .category(productDto.getCategory())
                .brand(productDto.getBrand())
                .build();
    }

    public static ProductDto mapProductToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .category(product.getCategory())
                .brand(product.getBrand())
                .build();
    }

    public static Shopper mapShopperDtoToShopper(ShopperDto shopperDto) {
        return Shopper.builder()
                .shopperId(shopperDto.getShopperId())
                .firstName(shopperDto.getFirstName())
                .lastName(shopperDto.getLastName())
                .build();
    }

    public static ShopperDto mapShopperToShopperDto(Shopper shopper) {
        return ShopperDto.builder()
                .shopperId(shopper.getShopperId())
                .firstName(shopper.getFirstName())
                .lastName(shopper.getLastName())
                .build();
    }
}
