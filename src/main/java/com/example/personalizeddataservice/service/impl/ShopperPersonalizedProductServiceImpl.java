package com.example.personalizeddataservice.service.impl;

import com.example.personalizeddataservice.domain.dto.*;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProduct;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProductKey;
import com.example.personalizeddataservice.repository.ProductRepository;
import com.example.personalizeddataservice.repository.ShopperPersonalizedProductRepository;
import com.example.personalizeddataservice.repository.ShopperRepository;
import com.example.personalizeddataservice.service.ShopperPersonalizedProductService;
import com.example.personalizeddataservice.util.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ShopperPersonalizedProductServiceImpl implements ShopperPersonalizedProductService {
    private final ProductRepository productRepository;
    private final ShopperRepository shopperRepository;
    private final ShopperPersonalizedProductRepository personalizedProductRepository;

    @Transactional
    @Override
    public ResultResponse saveShopperPersonalizedProduct(ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        if (shopperPersonalizedProductDto.getShelf() == null || shopperPersonalizedProductDto.getShelf().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be empty");
        }
        String shopperId = shopperPersonalizedProductDto.getShopperId();
        Shopper shopper = getShopper(shopperId);
        List<PersonalizedProduct> personalizedProducts = shopperPersonalizedProductDto.getShelf().stream()
                .map(p -> new PersonalizedProduct(this.getProduct(p.getProductId()), p.getRelevancyScore())).toList();
        List<ShopperPersonalizedProduct> shopperPersonalizedProducts = personalizedProducts.stream().map(p -> new ShopperPersonalizedProduct(new ShopperPersonalizedProductKey(shopper.getShopperId(),
                p.product().getProductId()), p.relevancyScore(), p.product(), shopper)).toList();
        log.info("ShopperPersonalizedProductServiceImpl: saveShopperPersonalizedProduct: shopperPersonalizedProducts {}", shopperPersonalizedProducts);
        this.personalizedProductRepository.saveAll(shopperPersonalizedProducts);
        return new ResultResponse(HttpStatus.OK, HttpStatus.OK.name());
    }


    @Override
    public ResultSetResponse<ProductDto> getPersonalizedProductsByShopperId(String shopperId, String category, String brand, int limit) {
        this.getShopper(shopperId);
        category = category == null || category.isBlank() ? null : category;
        brand = brand == null || brand.isBlank() ? null : brand;
        limit = Math.min(limit, 100);

        Page<Product> personalizedProducts = this.personalizedProductRepository.findPersonalizedProductsByShopperId(shopperId, category, brand, PageRequest.ofSize(limit));

        if (personalizedProducts.isEmpty()) {
            return new ResultSetResponse<>(0, 0, 0, Collections.emptyList());
        }
        return new ResultSetResponse<>(limit, personalizedProducts.getTotalElements(), personalizedProducts.getTotalPages(),
                personalizedProducts.getContent().stream().map(ObjectMapper::mapProductToProductDto).toList());
    }

    @Override
    public ResultSetResponse<ShopperDto> getShoppersByPersonalizedProductsId(String productId, int limit) {
        limit = Math.min(limit, 100);
        this.getProduct(productId);

        Page<Shopper> shoppers = this.personalizedProductRepository.findShoppersByPersonalizedProductId(productId, PageRequest.ofSize(limit));

        if (shoppers.isEmpty()) {
            return new ResultSetResponse<>(0, 0, 0, Collections.emptyList());
        }
        return new ResultSetResponse<>(limit, shoppers.getTotalElements(), shoppers.getTotalPages(),
                shoppers.getContent().stream().map(ObjectMapper::mapShopperToShopperDto).toList());
    }

    private Shopper getShopper(String shopperId) {
        return this.shopperRepository.findById(shopperId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopper is not found for the id " + shopperId));
    }

    private Product getProduct(String productId) {
        return this.productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found for the id " + productId));
    }

}
