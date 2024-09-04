package com.akshay.payment.repository;

import com.akshay.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
}
