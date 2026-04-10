package com.ritesh.product_catalog_server.service;

import com.ritesh.product_catalog_server.db.ProductEntity;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private HashOperations<String, Long, ProductEntity> hashOperations;

    CacheService(RedisTemplate<String,ProductEntity> rs){
        this.hashOperations = rs.opsForHash();
    }

    public ProductEntity getProduct(Long id){
           return hashOperations.get("Products",id);
    }

    public void addProduct(ProductEntity product){
        hashOperations.put("Products",product.getId(), product);
    }
    public void deleteProduct(Long id){
        hashOperations.delete("Products", id);
    }


}
