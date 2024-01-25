package com.example.personalizeddataservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopperPersonalizedProductDto {
    @NotNull(message = "Shopper id can not be null")
    @NotEmpty(message = "Shopper id can not be empty")
    private String shopperId;
    private Set<PersonalizedProductDto> shelf;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class PersonalizedProductDto {
        @NotNull(message = "Product id can not be null")
        @NotEmpty(message = "Product id can not be empty")
        private String productId;
        private Double relevancyScore;
    }
}
