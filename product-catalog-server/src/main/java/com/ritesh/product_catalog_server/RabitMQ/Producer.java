package com.ritesh.product_catalog_server.RabitMQ;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.dto.ProductEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishUpdate(ProductEntity product) {


        ProductEvent event = new ProductEvent(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );

        rabbitTemplate.convertAndSend(
                "product-exchange",
                "", //ignored in fanout
                event
        );
    }

}
