package com.example.app_api_2_12.model;

import java.util.List;

// Step 2.3: Rebuilding the response wrapper models for the vercel API.
public class ProductsResponse {
    private boolean status;
    private String message;
    private List<Product> data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Product> getData() {
        return data;
    }
}
