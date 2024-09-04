package com.akshay.order.model;

import com.akshay.order.payment.PaymentMethod;
import com.akshay.order.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderRequest(
        Integer orderId,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Please select a payment method")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be present")
        Integer customerId,
        @NotEmpty(message = "Please select at least one product")
        List<PurchaseRequest> products
) {
}
