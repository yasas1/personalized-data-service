package com.example.personalizeddataservice.controller;

import com.example.personalizeddataservice.domain.dto.ResultResponse;
import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.service.ShopperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/personalized-product-service/v1/shoppers")
@RestController
public class ShopperController {

    private final ShopperService shopperService;

    @Operation(summary = "Create Shopper",
            operationId = "createShopper",
            description = "This API is to create a shopper entity",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ShopperDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}")))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = "{\"shopperId\":\"shopperId\",\"firstName\":\"firstName\",\"lastName\":\"lastName\"}"))))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopperDto> createShopper(@Valid @RequestBody ShopperDto shopperDto) {
        return new ResponseEntity<>(this.shopperService.createShopper(shopperDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a shopper by id",
            operationId = "getProductById",
            description = "This API is to retrieve a shopper for a given id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ShopperDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}"))),
                    @ApiResponse(responseCode = "404", description = "Content Not Found",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"404\",\"message\":\"Shopper is not found for the id\",\"error\":\"Not Found\"}")))
            })
    @GetMapping(path = "/{shopperId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopperDto> getShopperById(@PathVariable String shopperId) {

        return new ResponseEntity<>(this.shopperService.getShopperById(shopperId), HttpStatus.OK);
    }

    @Operation(summary = "Save Shopper's Personalized Products with the shopper",
            operationId = "saveShopperPersonalizedProducts",
            description = "This API is to save given shopper's personalized products with the shopper",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResultResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}")))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = ShopperPersonalizedProductDto.class))))
    @PostMapping(path = "/personalized-products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> saveShopperPersonalizedProducts(@Valid @RequestBody ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        return new ResponseEntity<>(this.shopperService.saveShopperWithPersonalizedProducts(shopperPersonalizedProductDto), HttpStatus.OK);
    }
}
