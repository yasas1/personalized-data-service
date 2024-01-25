package com.example.personalizeddataservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopperDto {
    @NotNull(message = "Shopper Id name can not be null")
    @NotEmpty(message = "Shopper Id  name can not be empty")
    private String shopperId;
    @NotNull(message = "First name can not be null")
    @NotEmpty(message = "First name can not be empty")
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;
    @NotNull(message = "Last name can not be null")
    @NotEmpty(message = "Last name can not be empty")
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;
}
