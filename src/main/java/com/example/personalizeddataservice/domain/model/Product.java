package com.example.personalizeddataservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String productId;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "brand", nullable = false)
    private String brand;
    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    private Set<ShopperPersonalizedProduct> shopperPersonalizedProducts = new HashSet<>();
}
