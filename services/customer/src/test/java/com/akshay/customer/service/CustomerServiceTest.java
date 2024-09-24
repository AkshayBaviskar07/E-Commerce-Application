package com.akshay.customer.service;

import com.akshay.customer.model.Address;
import com.akshay.customer.model.Customer;
import com.akshay.customer.model.CustomerRequest;
import com.akshay.customer.model.CustomerResponse;
import com.akshay.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @MockBean
    private CustomerRepository customerRepo;
    @MockBean
    private CustomerMapper customerMapper;

    private CustomerRequest customerRequest;
    private Customer customer;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerRequest = new CustomerRequest(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                List.of(new Address(1, "123 Main St", "Apt 4", "12345", null))
        );

        customer = Customer.builder()
                .id(1)
                .firstName("john")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address(List.of(new Address(1, "123 Main St", "Apt 4", "12345", null)))
                .build();

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
        customerService = null;
        customerRepo = null;
        customerMapper = null;
        customer = null;
        customerRequest = null;
        customerResponse = null;
    }

    @Test
    void testCreateCustomer() {
        when(customerMapper.toCustomer(any(CustomerRequest.class))).thenReturn(customer);
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        Integer customerId = customerService.createCustomer(customerRequest);

        assertNotNull(customerId);
        assertEquals(customer.getId(), customerId);
    }

    @Test
    void testUpdateCustomer() {
        when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        assertDoesNotThrow(() -> customerService.updateCustomer(customerRequest));
    }

    @Test
    void testFindAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        when(customerMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        List<CustomerResponse> actualResponse = customerService.findAllCustomers();

        assertNotNull(actualResponse);
        assertEquals(1, customer.getId());
        assertEquals(customerResponse, actualResponse.get(0));
    }


    @Test
    void testFindById() {
        when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerService.findById(1);

        assertNotNull(actualResponse);
        assertEquals(customerResponse, actualResponse);
    }

    @Test
    void deleteCustomer() {
        doNothing().when(customerRepo).deleteById(anyInt());
        assertDoesNotThrow(() -> customerService.deleteCustomer(1));
    }
}