package com.akshay.product.exception;

public class ProductPurchaseException extends RuntimeException {
    String msg;
    public ProductPurchaseException(String msg) {
        super(msg);
    }
}
