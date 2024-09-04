package com.akshay.order.service;

import com.akshay.order.customer.CustomerClient;
import com.akshay.order.customer.CustomerResponse;
import com.akshay.order.exception.BusinessException;
import com.akshay.order.kafka.OrderProducer;
import com.akshay.order.model.Order;
import com.akshay.order.model.OrderConfirmation;
import com.akshay.order.model.OrderRequest;
import com.akshay.order.model.OrderResponse;
import com.akshay.order.orderline.OrderLineRequest;
import com.akshay.order.orderline.service.OrderLineService;
import com.akshay.order.payment.PaymentClient;
import com.akshay.order.payment.PaymentRequest;
import com.akshay.order.product.ProductClient;
import com.akshay.order.product.PurchaseRequest;
import com.akshay.order.product.PurchaseResponse;
import com.akshay.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        // Fetch customer
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot execute order:: No customer exists with provided id"));

        // purchase products
        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());

        // persist order
        Order order = this.repository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        PaymentRequest paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        // todo payment process
        paymentClient.requestOrderPayment(paymentRequest);

        // todo send the order confirmation notification --> kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
    return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the Id: %d", orderId)));
    }
}
