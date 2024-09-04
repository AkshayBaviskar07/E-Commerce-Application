package com.akshay.order.model;

import com.akshay.order.payment.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer orderId,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer customerId
) {
}
