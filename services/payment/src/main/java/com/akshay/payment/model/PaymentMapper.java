package com.akshay.payment.model;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .paymentId(request.id())
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }
}
