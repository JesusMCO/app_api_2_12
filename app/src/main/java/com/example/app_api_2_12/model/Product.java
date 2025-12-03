package com.example.app_api_2_12.model;

// Step 2.2: Rebuilding the data models correctly for fakestores.vercel.app API.
public class Product {
    private int id;
    private String title;
    private String price; // Price is a String in this API
    private String description;
    private String category;
    private String image;
    private Rating rating;

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
    public Rating getRating() { return rating; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(String price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setImage(String image) { this.image = image; }
    public void setRating(Rating rating) { this.rating = rating; }
}
