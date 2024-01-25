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
@Table(name = "shopper")
public class Shopper {
    @Id
    private String shopperId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @OneToMany(mappedBy = "shopper", cascade = {CascadeType.ALL})
    private Set<ShopperPersonalizedProduct> shopperPersonalizedProducts = new HashSet<>();
}
