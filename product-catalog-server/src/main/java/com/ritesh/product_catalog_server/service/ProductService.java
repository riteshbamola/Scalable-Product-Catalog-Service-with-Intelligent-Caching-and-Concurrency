package com.ritesh.product_catalog_server.service;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.db.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private CacheService cacheService;

    ProductService(ProductRepo productRepo, CacheService cacheService) {
        this.productRepo = productRepo;
        this.cacheService = cacheService;
    }

    public ProductEntity getProduct(Long id){
        ProductEntity product = cacheService.getProduct(id);
        System.out.println("Cache Value: "+ product);

        return  product != null ? product:productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product Not Found"));
    }

    public ProductEntity addProduct(ProductEntity product) {
        ProductEntity productEntity= productRepo.save(product);
        cacheService.addProduct(productEntity);
        return productEntity;
    }

    @Transactional
    public ProductEntity updateProduct(ProductEntity product){
        ProductEntity productEntity = productRepo.findById(product.getId()).orElseThrow(()-> new RuntimeException("Not Found"));
        productEntity.setPrice(product.getPrice());
        productEntity = productRepo.save(productEntity);
        return productEntity;
    }



}
