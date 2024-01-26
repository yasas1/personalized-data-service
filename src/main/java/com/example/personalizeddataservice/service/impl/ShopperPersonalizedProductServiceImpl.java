package com.example.personalizeddataservice.service.impl;

import com.example.personalizeddataservice.domain.dto.PersonalizedProduct;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProduct;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProductKey;
import com.example.personalizeddataservice.repository.ShopperPersonalizedProductRepository;
import com.example.personalizeddataservice.service.ProductService;
import com.example.personalizeddataservice.service.ShopperPersonalizedProductService;
import com.example.personalizeddataservice.service.ShopperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ShopperPersonalizedProductServiceImpl implements ShopperPersonalizedProductService {
    private final ProductService productService;
    private final ShopperService shopperService;
    private final ShopperPersonalizedProductRepository personalizedProductRepository;

    @Transactional
    @Override
    public void saveShopperPersonalizedProduct(ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        if (shopperPersonalizedProductDto.getShelf() == null || shopperPersonalizedProductDto.getShelf().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be empty");
        }
        String shopperId = shopperPersonalizedProductDto.getShopperId();
        Shopper shopper = this.shopperService.getShopperById(shopperId);
        List<PersonalizedProduct> personalizedProducts = shopperPersonalizedProductDto.getShelf().stream()
                .map(shelf -> new PersonalizedProduct(this.productService.getProductById(shelf.getProductId()), shelf.getRelevancyScore())).toList();
        List<ShopperPersonalizedProduct> shopperPersonalizedProducts = personalizedProducts.stream().map(personalizedProduct -> new ShopperPersonalizedProduct(new ShopperPersonalizedProductKey(shopper.getShopperId(),
                personalizedProduct.product().getProductId()), personalizedProduct.relevancyScore(), personalizedProduct.product(), shopper)).toList();
        log.info("ShopperPersonalizedProductServiceImpl: saveShopperPersonalizedProduct: shopperPersonalizedProducts {}", shopperPersonalizedProducts);
        this.personalizedProductRepository.saveAll(shopperPersonalizedProducts);
    }


    @Override
    public Page<Product> getPersonalizedProductsByShopperId(String shopperId, String category, String brand, int limit) {
        this.shopperService.getShopperById(shopperId);
        category = category == null || category.isBlank() ? null : category;
        brand = brand == null || brand.isBlank() ? null : brand;
        limit = Math.min(limit, 100);
        log.info("ShopperPersonalizedProductServiceImpl: getPersonalizedProductsByShopperId: shopperId: {} category: {} brand: {} limit: {}", shopperId, category, brand, limit);
        return this.personalizedProductRepository.findPersonalizedProductsByShopperId(shopperId, category, brand, PageRequest.ofSize(limit));
    }

    @Override
    public Page<Shopper> getShoppersByPersonalizedProductsId(String productId, int limit) {
        limit = Math.min(limit, 100);
        this.productService.getProductById(productId);
        log.info("ShopperPersonalizedProductServiceImpl: getShoppersByPersonalizedProductsId: productId: {} limit: {}", productId, limit);
        return this.personalizedProductRepository.findShoppersByPersonalizedProductId(productId, PageRequest.ofSize(limit));
    }
}
