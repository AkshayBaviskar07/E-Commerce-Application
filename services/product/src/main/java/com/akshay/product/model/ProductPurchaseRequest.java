package com.akshay.product.model;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id required")
        Integer productId,
        @NotNull(message = "Add quantity")
        Double quantity
) {
}
