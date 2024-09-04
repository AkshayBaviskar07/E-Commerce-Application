package com.akshay.product.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name required")
        String name,
        @NotNull(message = "Product description required")
        String description,
        @NotNull(message = "Product price required")
        BigDecimal price,
        @NotNull(message = "Available quantity should be positive")
        Double availableQuantity,
        @NotNull(message = "Product category required")
        Integer categoryId
) {
}
