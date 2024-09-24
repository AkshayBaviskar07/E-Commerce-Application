package com.akshay.product.controller;

import com.akshay.product.model.ProductPurchaseRequest;
import com.akshay.product.model.ProductPurchaseResponse;
import com.akshay.product.model.ProductRequest;
import com.akshay.product.model.ProductResponse;
import com.akshay.product.service.ProductService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ProductControllerTest is a test class for the ProductController.
 * It uses Spring's MockMvc to perform and verify HTTP requests and responses
 * The tests cover various endpoints of the ProductController <br>
 *
 * <b>Dependencies</b>:
 * - Spring Boot
 * - Spring Boot Starter Test
 * - Mockito
 * - [JUnit](<a href="https://junit.org/junit5/">...</a>) <br>
 *
 * <b>Annotations</b>:
 * @WebMvcTest: Used to test the ProductController in isolation
 * @Autowired: Used to inject the MockMvc instance.
 * @MockBean: Used to mock the ProductService.
 * @BeforeEach: Used to set up the test environment before each test
 * @AfterEach: Used to clean up the test environment after each test
 * @Test: Used to mark methods as test cases
 *
 * <h3>Author: Akshay</h3>
 */

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private ProductRequest request;
    private ProductResponse response;
    private List<ProductResponse> productResponseList;
    private ProductPurchaseRequest productPurchaseRequest;
    private ProductPurchaseResponse productPurchaseResponse;
    private List<ProductPurchaseRequest> productPurchaseRequestList;
    private List<ProductPurchaseResponse> productPurchaseResponseList;

    /**
     * Sets up the test environment before each test.
     * Initializes the MockMvc instance and create mock data for testing.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();

        request = new ProductRequest(
                1,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                10.0,
                1
        );

        response = new ProductResponse(
                1,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                10.0,
                1,
                "Category Name",
                "Category Description"
        );

        productResponseList = Arrays.asList(response);
        productPurchaseRequest = new ProductPurchaseRequest(1, 2.0);
        productPurchaseRequestList = Arrays.asList(productPurchaseRequest);
        productPurchaseResponse = new ProductPurchaseResponse(1, 2.0, "Product1", "Description1", new BigDecimal("100.00"));
        productPurchaseResponseList = Arrays.asList(productPurchaseResponse);
    }

    /**
     * Cleans up the test environment after each test.
     * Sets all instance variable to null.
     */
    @AfterEach
    void tearDown() {
        mockMvc = null;
        productService = null;
        request = null;
        response = null;
        productResponseList = null;
        productPurchaseRequest = null;
        productPurchaseRequestList = null;
        productPurchaseResponse = null;
        productPurchaseResponseList = null;
    }

    /**
     * Tests the createProduct endpoint.
     * Verifies that the product is created successfully and the response is as expected.
     */
    @Test
    void testCreateProduct() throws Exception{

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(1);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Product\",\"description\":\"Test Description\",\"price\":100.00,\"availableQuantity\":10.0,\"categoryId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    /**
     * Tests the purchaseProducts endpoint.
     * Verifies that the products are purchased successfully and the response is as expected.
     */
    @Test
    void testPurchaseProducts()throws Exception {
        when(productService.purchaseProducts(any(List.class))).thenReturn(productPurchaseResponseList);

        mockMvc.perform(post("/api/products/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"productId\": 1, \"quantity\": 2.0}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].quantity").value(2.0));
    }

    /**
     * Tests the findById endpoint.
     * Verifies that the product is found successfully and the response is as expected.
     */
    @Test
    void testFindById() throws Exception{
        when(productService.findById(1)).thenReturn(response);
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    /**
     * Tests the findAll endpoint.
     * Verifies that all products are found successfully and the response is as expected.
     */
    @Test
    void findAll() throws Exception{
        when(productService.findAll()).thenReturn(productResponseList);
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }
}