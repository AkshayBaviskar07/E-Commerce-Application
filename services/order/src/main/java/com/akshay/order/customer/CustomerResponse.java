package com.akshay.order.customer;

public record CustomerResponse(
        Integer customerId,
        String firstname,
        String lastname,
        String email
) {
}
