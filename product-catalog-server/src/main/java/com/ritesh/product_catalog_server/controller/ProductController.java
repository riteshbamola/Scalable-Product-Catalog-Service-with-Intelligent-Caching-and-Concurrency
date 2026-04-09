package com.ritesh.product_catalog_server.controller;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.service.ProductService;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
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
    @PutMapping("/product/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductEntity product) {

        ProductEntity updatedProduct = productService.updateProduct(product);

        Map<String, Object> response = new HashMap<>();
        response.put("data", updatedProduct);

        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<Map<String, String>> handleOptimisticLockException(OptimisticLockException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", "Product was updated by another user. Please retry.");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
