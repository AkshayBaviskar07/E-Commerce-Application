package com.akshay.customer.controller;

import com.akshay.customer.model.Address;
import com.akshay.customer.model.CustomerRequest;
import com.akshay.customer.model.CustomerResponse;
import com.akshay.customer.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    private CustomerRequest request;
    private CustomerResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new CustomerRequest(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                List.of(new Address(1, "123 Main St", "Apt 4", "12345", null))
        );

        response = new CustomerResponse(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                List.of(new Address(1, "123 Main St", "Apt 4", "12345", null))
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @AfterEach
    void tearDown() {
        mockMvc = null;
        customerService = null;
        response = null;
        request = null;
    }

    @Test
    void testCreateCustomer() throws Exception{
        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(1);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":[{\"id\":1,\"street\":\"123 Main St\",\"houseNumber\":\"Apt 4\",\"zipCode\":\"12345\"}]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

    }

    @Test
    void testUpdateCustomer() throws Exception {
        doNothing().when(customerService).updateCustomer(any(CustomerRequest.class));
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":[{\"id\":1,\"street\":\"123 Main St\",\"houseNumber\":\"Apt 4\",\"zipCode\":\"12345\"}]}"))
                .andExpect(status().isAccepted());
    }

    @Test
    void testFindAll() throws Exception{
        when(customerService.findAllCustomers()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void testExistsById() throws Exception {
        when(customerService.existsById(anyInt())).thenReturn(true);
        mockMvc.perform(get("/api/customers/exist/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testFindById() throws Exception{
        when(customerService.findById(anyInt())).thenReturn(response);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testDeleteCustomer() throws Exception{
        doNothing().when(customerService).deleteCustomer(anyInt());

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isAccepted());
    }
}