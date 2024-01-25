package com.example.personalizeddataservice.service.impl;

import com.example.personalizeddataservice.domain.dto.ResultResponse;
import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProduct;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProductKey;
import com.example.personalizeddataservice.repository.ProductRepository;
import com.example.personalizeddataservice.repository.ShopperRepository;
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
    private final ProductRepository productRepository;

    @Override
    public ShopperDto createShopper(ShopperDto shopperDto) {
        Shopper shopper = this.shopperRepository.save(ObjectMapper.mapShopperDtoToShopper(shopperDto));
        return ObjectMapper.mapShopperToShopperDto(shopper);
    }

    @Transactional
    @Override
    public ResultResponse saveShopperWithPersonalizedProducts(ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        shopperPersonalizedProductDto.getShelf().forEach(p -> this.productRepository.findById(p.getProductId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found for the id " + p.getProductId())));
        Shopper shopper = Shopper.builder().shopperId(shopperPersonalizedProductDto.getShopperId()).build();
        Set<ShopperPersonalizedProduct> shopperPersonalizedProducts = shopperPersonalizedProductDto.getShelf().stream().map(shelf -> {
            Product product = this.productRepository.findById(shelf.getProductId()).
                    orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found for the id " + shelf.getProductId()));
            return ShopperPersonalizedProduct.builder().id(new ShopperPersonalizedProductKey(shopper.getShopperId(), product.getProductId()))
                    .relevancyScore(shelf.getRelevancyScore())
                    .shopper(shopper)
                    .product(product)
                    .build();
        }).collect(Collectors.toSet());
        shopper.setShopperPersonalizedProducts(shopperPersonalizedProducts);
        this.shopperRepository.save(shopper);
        return new ResultResponse(HttpStatus.OK, HttpStatus.OK.name());
    }

    @Override
    public ShopperDto getShopperById(String shopperId) {
        Shopper shopper = this.shopperRepository.findById(shopperId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopper is not found for the id " + shopperId));
        return ObjectMapper.mapShopperToShopperDto(shopper);
    }

}
