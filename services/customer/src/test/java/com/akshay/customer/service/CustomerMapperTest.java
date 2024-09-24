package com.akshay.customer.service;

import com.akshay.customer.model.Address;
import com.akshay.customer.model.Customer;
import com.akshay.customer.model.CustomerRequest;
import com.akshay.customer.model.CustomerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerMapperTest {

    private CustomerMapper mapper;
    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;
    private Customer customer;

    @BeforeEach
    void setUp() {

        mapper = new CustomerMapper();

        customer = Customer.builder()
                .id(1)
                .firstName("john")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address(List.of(new Address(1, "123 Main St", "Apt 4", "12345", null)))
                .build();

        customerRequest = new CustomerRequest(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                List.of(new Address(1, "123 Main St", "Apt 4", "12345", null))
        );

        customerResponse = new CustomerResponse(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                List.of(new Address(1, "123 Main St", "Apt 4", "12345", null))
        );
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        customerRequest = null;
        customerResponse = null;
    }

    @Test
    void testToCustomer() {
        Customer actualResponse = mapper.toCustomer(customerRequest);
        assertNotNull(actualResponse);
        assertEquals(customerRequest.id(), actualResponse.getId());
        assertEquals(customerRequest.firstName(), actualResponse.getFirstName());
        assertEquals(customerRequest.lastName(), actualResponse.getLastName());
        assertEquals(customerRequest.address(), actualResponse.getAddress());
        assertEquals(customerRequest.email(), actualResponse.getEmail());
    }

    @Test
    void fromCustomer() {
        CustomerResponse actualResponse = mapper.fromCustomer(customer);

        assertNotNull(actualResponse);
        assertEquals(customer.getId(), actualResponse.id());
        assertEquals(customer.getFirstName(), actualResponse.firstName());
        assertEquals(customer.getLastName(), actualResponse.lastName());
        assertEquals(customer.getEmail(), actualResponse.email());
    }
}