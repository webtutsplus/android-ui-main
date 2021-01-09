package com.webtutsplus.ecommerceapp.Model;

public class Product {
    private long id;
    private String name;
    private String imageURL;
    private double price;
    private String description;
    private long categoryId;

    public Product(long id, String name, String image, double price, String description, long categoryId) {
        this(id, name, image, price, description);
        this.categoryId = categoryId;
    }


    public Product(long id, String name, String image, double price, String description) {
        this.id = id;
        this.name = name;
        this.imageURL = image;
        this.price = price;
        this.description = description;
    }




    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
