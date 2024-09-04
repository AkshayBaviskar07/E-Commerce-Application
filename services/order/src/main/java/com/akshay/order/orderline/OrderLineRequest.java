package com.akshay.order.orderline;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
