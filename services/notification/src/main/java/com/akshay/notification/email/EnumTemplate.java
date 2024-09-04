package com.akshay.notification.email;

import lombok.Getter;

public enum EnumTemplate {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation")
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EnumTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
