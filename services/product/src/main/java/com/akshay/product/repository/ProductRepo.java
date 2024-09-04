package com.akshay.product.repository;

import com.akshay.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> productIds);
}
