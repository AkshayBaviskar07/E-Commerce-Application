package com.akshay.product.service;

import com.akshay.product.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @AfterEach
    void tearDown() {
        productMapper = null;
    }

    @Test
    void testToProduct() {
        ProductRequest request = new ProductRequest(
                1,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                10.0,
                1
        );

        Product actualResponse = productMapper.toProduct(request);

        assertNotNull(actualResponse);
        assertEquals(request.id(), actualResponse.getId());
        assertEquals(request.name(), actualResponse.getName());
        assertEquals(request.price(), actualResponse.getPrice());
        assertEquals(request.description(), actualResponse.getDescription());
        assertEquals(request.categoryId(), actualResponse.getCategory().getCategory_id());
        assertEquals(request.availableQuantity(), actualResponse.getAvailableQuantity());
    }

    @Test
    void testToProductResponse() {
        Category category = Category.builder()
                .category_id(1)
                .name("Category name")
                .description("Category description")
                .build();

        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .availableQuantity(1.0)
                .category(category)
                .build();

        ProductResponse actualResponse = productMapper.toProductResponse(product);

        assertNotNull(actualResponse);
        assertEquals(product.getId(), actualResponse.id());
        assertEquals(product.getName(), actualResponse.name());
        assertEquals(product.getDescription(), actualResponse.description());
        assertEquals(product.getPrice(), actualResponse.price());
        assertEquals(product.getCategory().getCategory_id(), actualResponse.categoryId());
        assertEquals(product.getAvailableQuantity(), actualResponse.availableQuantity());
    }

    @Test
    void testToProductPurchaseResponse() {
        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .availableQuantity(1.0)
                .build();

        Double quantity = 2.0;

        ProductPurchaseResponse productPurchaseResponse = productMapper.toProductPurchaseResponse(product, quantity);

        assertNotNull(productPurchaseResponse);
        assertNotNull(productPurchaseResponse);
        assertEquals(product.getId(), productPurchaseResponse.productId());
        assertEquals(quantity, productPurchaseResponse.quantity());
        assertEquals(product.getName(), productPurchaseResponse.name());
        assertEquals(product.getDescription(), productPurchaseResponse.description());
        assertEquals(product.getPrice(), productPurchaseResponse.price());

    }
}