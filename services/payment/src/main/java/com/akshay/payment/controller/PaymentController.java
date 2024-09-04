package com.akshay.payment.controller;

import com.akshay.payment.model.PaymentRequest;
import com.akshay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request) {
        return ResponseEntity.ok(service.createPayments(request));
    }

}
