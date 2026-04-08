package com.ritesh.product_catalog_server.controller;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {


    private ProductService productService;

    ProductController(ProductService productService){
        this.productService= productService;
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<Map<String, ProductEntity>> getProduct(@PathVariable Long id) {
        Map<String, ProductEntity> response = new HashMap<>();

        ProductEntity product = productService.getProduct(id);
        response.put("product", product);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/product")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductEntity product) {
        ProductEntity savedProduct = productService.addProduct(product);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product created successfully");
        response.put("product", savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
