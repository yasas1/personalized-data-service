package com.example.personalizeddataservice.controller;

import com.example.personalizeddataservice.domain.dto.ProductDto;
import com.example.personalizeddataservice.service.ProductService;
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
@RequestMapping("/personalized-product-service/v1/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Create product",
            operationId = "createProduct",
            description = "This API is to create a product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}")))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = "{\"productId\":\"productId\",\"category\":\"category\",\"brand\":\"brand\"}"))))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(this.productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a product by id",
            operationId = "getProductById",
            description = "This API is to retrieve a product for a given product id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}"))),
                    @ApiResponse(responseCode = "404", description = "Content Not Found",
                            content = @Content(examples = @ExampleObject(value = "{\"status\":\"404\",\"message\":\"Product is not found for the id\",\"error\":\"Not Found\"}")))
            })
    @GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        return new ResponseEntity<>(this.productService.getProductById(productId), HttpStatus.OK);
    }
}
