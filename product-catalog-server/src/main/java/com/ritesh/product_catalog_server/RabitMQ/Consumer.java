package com.ritesh.product_catalog_server.RabitMQ;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.db.ProductRepo;
import com.ritesh.product_catalog_server.dto.ProductEvent;
import com.ritesh.product_catalog_server.service.CacheService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ProductRepo productRepo;


    @RabbitListener(queues = "product-cache-queue")
    public void handleUpdate(ProductEvent event) {
        ProductEntity product = productRepo.findById(event.id()).orElseThrow();
        System.out.println("Consumer Price got = " + product.getPrice());
        cacheService.addProduct(product);
    }
}