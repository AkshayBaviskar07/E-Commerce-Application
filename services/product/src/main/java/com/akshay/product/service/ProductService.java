package com.akshay.product.service;

import com.akshay.product.exception.ProductPurchaseException;
import com.akshay.product.model.*;
import com.akshay.product.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repo.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> storedProduct = repo.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProduct.size()){
            throw new ProductPurchaseException("One or more product does not exists");
        }

        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i=0; i<storedProduct.size(); i++) {
            var product = storedProduct.get(i);
            var productRequest = storedRequest.get(i);


            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient quantity for product id: " + product.getId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repo.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repo.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
