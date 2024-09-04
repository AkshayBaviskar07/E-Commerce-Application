package com.akshay.payment.service;

import com.akshay.payment.model.Payment;
import com.akshay.payment.model.PaymentMapper;
import com.akshay.payment.model.PaymentRequest;
import com.akshay.payment.notification.NotificationProducer;
import com.akshay.payment.notification.PaymentNotificationRequest;
import com.akshay.payment.repository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayments(PaymentRequest request) {
        Payment payment = repository.save(mapper.toPayment(request));
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );

        return payment.getPaymentId();
    }
}
