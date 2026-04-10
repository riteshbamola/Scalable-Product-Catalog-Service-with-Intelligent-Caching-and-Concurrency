package com.ritesh.product_catalog_server.handler;

import com.ritesh.product_catalog_server.RabitMQ.Producer;
import com.ritesh.product_catalog_server.db.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class ProductEventHandler {

    @Autowired
    private Producer producer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductEntity product) {
        producer.publishUpdate(product); // ✅ safe
    }
}