package com.example.personalizeddataservice.service.impl;

import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProduct;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProductKey;
import com.example.personalizeddataservice.repository.ShopperRepository;
import com.example.personalizeddataservice.service.ProductService;
import com.example.personalizeddataservice.service.ShopperService;
import com.example.personalizeddataservice.util.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShopperServiceImpl implements ShopperService {

    private final ShopperRepository shopperRepository;
    private final ProductService productService;

    @Override
    public Shopper createShopper(ShopperDto shopperDto) {
        return this.shopperRepository.save(ObjectMapper.mapShopperDtoToShopper(shopperDto));
    }

    @Transactional
    @Override
    public void saveShopperWithPersonalizedProducts(ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        Shopper shopper = Shopper.builder().shopperId(shopperPersonalizedProductDto.getShopperId()).build();
        Set<ShopperPersonalizedProduct> shopperPersonalizedProducts = shopperPersonalizedProductDto.getShelf().stream().map(shelf -> {
            Product product = this.productService.getProductById(shelf.getProductId());
            return ShopperPersonalizedProduct.builder().id(new ShopperPersonalizedProductKey(shopper.getShopperId(), product.getProductId()))
                    .relevancyScore(shelf.getRelevancyScore())
                    .shopper(shopper)
                    .product(product)
                    .build();
        }).collect(Collectors.toSet());
        shopper.setShopperPersonalizedProducts(shopperPersonalizedProducts);
        this.shopperRepository.save(shopper);
    }

    @Override
    public Shopper getShopperById(String shopperId) {
        return this.shopperRepository.findById(shopperId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopper is not found for the id " + shopperId));
    }

}
