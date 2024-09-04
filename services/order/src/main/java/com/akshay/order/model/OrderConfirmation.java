package com.akshay.order.model;

import com.akshay.order.customer.CustomerResponse;
import com.akshay.order.payment.PaymentMethod;
import com.akshay.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
