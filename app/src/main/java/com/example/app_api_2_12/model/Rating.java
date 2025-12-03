package com.example.app_api_2_12.model;

// Step 2.1: Rebuilding the data models correctly for fakestores.vercel.app API.
public class Rating {
    private String rate;
    private String count;

    public String getRate() {
        return rate;
    }

    public String getCount() {
        return count;
    }

    // Setters for creating/updating
    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
