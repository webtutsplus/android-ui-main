package com.webtutsplus.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private String description;
    private long categoryId;
    private long id;
    private String imageURL;
    private String name;
    private double price;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Product() { }

    public Product(long categoryId, String name, String imageURL, double price, String description) {
        this.description = description;
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() { return  categoryId; }

    public void setId(long id) { this.categoryId = categoryId; }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
