package com.akshay.product.service;

import com.akshay.product.exception.ProductPurchaseException;
import com.akshay.product.model.*;
import com.akshay.product.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    private ProductRequest productRequest;
    private Product product;
    private ProductResponse productResponse;
    private ProductPurchaseRequest productPurchaseRequest;
    private ProductPurchaseResponse productPurchaseResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productRequest = new ProductRequest(
                1,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                10.0,
                1
        );

        product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .availableQuantity(10.0)
                .category(Category.builder().category_id(1).build())
                .build();

        productResponse = new ProductResponse(
                1,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                10.0,
                1,
                "Category name",
                "Category Description"
        );

        productPurchaseRequest = new ProductPurchaseRequest(1, 2.0);
        productPurchaseResponse = new ProductPurchaseResponse(1, 2.0, "Test Product", "Test Description", BigDecimal.valueOf(100.00));
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Tests the createProduct method.
     * Verifies that the product is created successfully and the response is as expected.
     */
    @Test
    void testCreateProduct() {
        when(productMapper.toProduct(any(ProductRequest.class))).thenReturn(product);
        when(productRepo.save(product)).thenReturn(product);

        Integer productId = productService.createProduct(productRequest);
        //assertNotNull(productId);
        assertEquals(1, productId);
    }

    /**
     * Tests the createProduct method with validation error.
     * Verifies that an exception is thrown when the product request is invalid.
     */
    @Test
    void testCreateProductWithValidationError() {
        productRequest = new ProductRequest(
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertThrows(NullPointerException.class, () -> productService.createProduct(productRequest));
    }

    /**
     * Tests the purchaseProducts method.
     * Verifies that the products are purchased successfully and the response is as expected.
     */
    @Test
    void purchaseProducts() {
        List<ProductPurchaseRequest> purchaseRequests = Arrays.asList(productPurchaseRequest);
        List<Product> products = Arrays.asList(product);
        List<ProductPurchaseResponse> expectedResponse = Arrays.asList(productPurchaseResponse);

        when(productRepo.findAllByIdInOrderById(anyList())).thenReturn(products);
        when(productMapper.toProductPurchaseResponse(any(Product.class), anyDouble())).thenReturn(productPurchaseResponse);

        List<ProductPurchaseResponse> actualResponses = productService.purchaseProducts(purchaseRequests);

        assertNotNull(actualResponses);
        assertEquals(expectedResponse.size(), actualResponses.size());
        assertEquals(expectedResponse.get(0).productId(), actualResponses.get(0).productId());
    }

    @Test
    void testPurchaseProductsWithInsufficientQuantity() {
        product.setAvailableQuantity(1.0);
        List<ProductPurchaseRequest> purchaseRequests = Arrays.asList(productPurchaseRequest);
        List<Product> products = Arrays.asList(product);

        when(productRepo.findAllByIdInOrderById(anyList())).thenReturn(products);

        assertThrows(ProductPurchaseException.class, () -> productService.purchaseProducts(purchaseRequests));
    }

    @Test
    void testFindById() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);

        ProductResponse actualResponse = productService.findById(1);

        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.id());
    }

    @Test
    void testFindByIdWithNonExistentProduct() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Product> products = Arrays.asList(product);
        List<ProductResponse> expectedResponse = Arrays.asList(productResponse);

        when(productRepo.findAll()).thenReturn(products);
        when(productMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);

        List<ProductResponse> actualResponse = productService.findAll();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).id(), actualResponse.get(0).id());
    }
}