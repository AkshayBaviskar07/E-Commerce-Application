package com.akshay.customer.service;

import com.akshay.customer.model.Customer;
import com.akshay.customer.model.CustomerRequest;
import com.akshay.customer.model.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {

        if (request == null) {
            return null;
        }

        return Customer.builder()
                .id(request.id())
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
