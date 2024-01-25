package com.example.personalizeddataservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ShopperPersonalizedProductKey implements Serializable {
    @Column(name = "shopper_id")
    private String shopperId;
    @Column(name = "product_id")
    private String productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopperPersonalizedProductKey that = (ShopperPersonalizedProductKey) o;
        return Objects.equals(productId, that.productId) && Objects.equals(shopperId, that.shopperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, shopperId);
    }
}
