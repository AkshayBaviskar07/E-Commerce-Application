package com.akshay.notification.kafka.order;

public record Customer(
        Integer customerId,
        String firstName,
        String lastName,
        String email
) {
}
