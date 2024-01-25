package com.example.personalizeddataservice.repository;

import com.example.personalizeddataservice.domain.model.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopperRepository extends JpaRepository<Shopper, String> {
}
