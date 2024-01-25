package com.example.personalizeddataservice.repository;

import com.example.personalizeddataservice.domain.model.Product;
import com.example.personalizeddataservice.domain.model.Shopper;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProduct;
import com.example.personalizeddataservice.domain.model.ShopperPersonalizedProductKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopperPersonalizedProductRepository extends JpaRepository<ShopperPersonalizedProduct, ShopperPersonalizedProductKey> {

    @Query("SELECT p FROM ShopperPersonalizedProduct as spp INNER JOIN Product as p on spp.id.productId = p.productId WHERE spp.id.shopperId = :shopperId  " +
            "AND (:category IS NULL OR p.category = :category) AND (:brand IS NULL OR p.brand = :brand) ORDER BY p.category ASC")
    Page<Product> findPersonalizedProductsByShopperId(@Param("shopperId") String shopperId, @Param("category") String category, @Param("brand") String brand, Pageable pageable);

    @Query("SELECT s FROM ShopperPersonalizedProduct as spp INNER JOIN Shopper as s on spp.id.shopperId = s.shopperId WHERE spp.id.productId = :productId ORDER BY s.firstName ASC")
    Page<Shopper> findShoppersByPersonalizedProductId(@Param("productId") String productId, Pageable pageable);

}
