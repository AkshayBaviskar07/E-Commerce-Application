package com.akshay.order.service;

import com.akshay.order.model.Order;
import com.akshay.order.model.OrderRequest;
import com.akshay.order.model.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        if (request == null) {
            return null;
        }
        return Order.builder()
                .id(request.orderId())
                .customerId(request.customerId())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
