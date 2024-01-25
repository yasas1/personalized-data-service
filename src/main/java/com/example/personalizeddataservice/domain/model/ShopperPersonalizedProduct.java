package com.example.personalizeddataservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "shopper_personalized_product")
public class ShopperPersonalizedProduct {
    @EmbeddedId
    private ShopperPersonalizedProductKey id;
    @Column(name = "relevancy_score", nullable = false)
    private double relevancyScore;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @MapsId("productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopperId", referencedColumnName = "shopperId")
    @MapsId("shopperId")
    private Shopper shopper;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopperPersonalizedProduct that = (ShopperPersonalizedProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
