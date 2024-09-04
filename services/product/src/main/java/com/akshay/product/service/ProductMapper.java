package com.akshay.product.service;

import com.akshay.product.model.*;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .category(
                        Category.builder()
                                .category_id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailableQuantity(),
                product.getCategory().getCategory_id(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, Double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                quantity,
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
