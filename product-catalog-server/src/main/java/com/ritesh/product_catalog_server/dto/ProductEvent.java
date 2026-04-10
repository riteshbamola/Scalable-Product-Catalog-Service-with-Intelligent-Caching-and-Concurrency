package com.ritesh.product_catalog_server.dto;

import java.math.BigDecimal;

public record ProductEvent(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity
) {}