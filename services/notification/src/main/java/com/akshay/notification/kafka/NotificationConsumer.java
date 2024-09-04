package com.akshay.notification.kafka;

import com.akshay.notification.email.EmailService;
import com.akshay.notification.kafka.order.OrderConfirmation;
import com.akshay.notification.kafka.payment.PaymentConfirmation;
import com.akshay.notification.model.Notification;
import com.akshay.notification.model.NotificationType;
import com.akshay.notification.repository.NotificationRepo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.akshay.notification.model.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepo repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment-topic Topic:: %s ", paymentConfirmation));
        repository.save(
               Notification.builder()
                       .type(PAYMENT_CONFIRMATION)
                       .notificationDate(LocalDateTime.now())
                       .paymentConfirmation(paymentConfirmation)
                       .build()
        );

        String customerName = paymentConfirmation.customerFirstName() + " "
                    + paymentConfirmation.customerLastName();

        emailService.sendPaymentSuccessMail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment"));
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        String customerName = orderConfirmation.customer().firstName() + " "
                + orderConfirmation.customer().lastName();

        emailService.orderConfirmationMail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}
