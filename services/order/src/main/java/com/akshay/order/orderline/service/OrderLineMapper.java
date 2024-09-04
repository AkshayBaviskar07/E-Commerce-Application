package com.akshay.order.orderline.service;

import com.akshay.order.model.Order;
import com.akshay.order.orderline.OrderLine;
import com.akshay.order.orderline.OrderLineRequest;
import com.akshay.order.orderline.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.orderId())
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
