package com.example.personalizeddataservice.controller;

import com.example.personalizeddataservice.domain.dto.ShopperDto;
import com.example.personalizeddataservice.domain.dto.ResultResponse;
import com.example.personalizeddataservice.domain.dto.ResultSetResponse;
import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.domain.dto.ShopperPersonalizedProductDto;
import com.example.personalizeddataservice.service.ShopperPersonalizedProductService;
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
@RequestMapping("/personalized-product-service/v1/shopper-personalized-products")
@RestController
public class ShopperPersonalizedProductController {
    private final ShopperPersonalizedProductService shopperPersonalizedProductService;

    @Operation(summary = "Save Shopper's Personalized Products",
            operationId = "saveShopperPersonalizedProducts",
            description = "This API is to save given shopper's personalized products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResultResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}")))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = ShopperPersonalizedProductDto.class))))
    @PostMapping(path = "/shoppers/{shopperId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> saveShopperPersonalizedProducts(@PathVariable String shopperId, @Valid @RequestBody ShopperPersonalizedProductDto shopperPersonalizedProductDto) {
        shopperPersonalizedProductDto.setShopperId(shopperId);
        this.shopperPersonalizedProductService.saveShopperPersonalizedProduct(shopperPersonalizedProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "List products by product shopper id",
            operationId = "getProductByShopperId",
            description = "This API is to retrieve personalized products for a given shopper id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ResultSetResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Content Not Found",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"404\",\"message\":\"Shopper is not found for the id\",\"error\":\"Not Found\"}")))
            })
    @GetMapping(path = "/shoppers/{shopperId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultSetResponse<ProductDto>> getPersonalizedProductsByShopperId(@PathVariable String shopperId,
                                                                                            @RequestParam(required = false, defaultValue = "10") int limit,
                                                                                            @RequestParam(required = false) String category,
                                                                                            @RequestParam(required = false) String brand) {
        return new ResponseEntity<>(this.shopperPersonalizedProductService.getPersonalizedProductsByShopperId(shopperId, category, brand, limit), HttpStatus.OK);
    }

    @Operation(summary = "List shoppers by product id",
            operationId = "getShoppersByPersonalizedProductsId",
            description = "This API is to retrieve shoppers for a given personalized products id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ResultSetResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Content Not Found",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"404\",\"message\":\"Product is not found for the id\",\"error\":\"Not Found\"}")))
            })
    @GetMapping(path = "/products/{productId}/shoppers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultSetResponse<ShopperDto>> getShoppersByPersonalizedProductsId(@PathVariable String productId,
                                                                                             @RequestParam(required = false, defaultValue = "10") int limit) {
        return new ResponseEntity<>(this.shopperPersonalizedProductService.getShoppersByPersonalizedProductsId(productId, limit), HttpStatus.OK);
    }
}
