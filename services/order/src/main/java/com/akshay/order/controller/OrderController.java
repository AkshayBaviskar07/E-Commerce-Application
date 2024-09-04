package com.akshay.order.controller;

import com.akshay.order.model.OrderRequest;
import com.akshay.order.model.OrderResponse;
import com.akshay.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(service.findById(orderId));
    }
}
