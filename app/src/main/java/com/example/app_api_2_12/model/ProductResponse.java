package com.example.app_api_2_12.model;

// Step 2.4: Rebuilding the response wrapper for a single product.
public class ProductResponse {
    private boolean status;
    private String message;
    private Product data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Product getData() {
        return data;
    }
}
