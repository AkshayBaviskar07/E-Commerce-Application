package com.akshay.product.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPurchaseResponse(

        Integer productId,
        Double quantity,
        String name,
        String description,
        BigDecimal price
) {
}
