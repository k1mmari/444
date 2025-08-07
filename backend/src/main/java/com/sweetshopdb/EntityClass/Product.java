/**
 * This java class represents the product object in the backend code and contains the necessary
 * attributes and methods for managing product data logic.
 * 
 * @see Category.java for product categories (ENUM)
 * @author 444 Team Alfredo
 * @version 1.0
 */

package com.sweetshopdb.EntityClass;

import jakarta.persistence.*;
import com.sweetshopdb.ENUMClass.Category;

@Entity
@Table(name = "product")
public class Product {
    
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private int productID;

    @Column(name = "prod_name")
    private String productName;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;

    @Column(name = "img_url")
    private String imageURL;

    @Column(name = "stock_qty")
    private int stockQuantity;

    //no arg constructor
    public Product() {}

    public Product(int productID, String productName, double price, Category category, String description, String imageURL, int stockQuantity)
    {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.imageURL = imageURL;
        this.stockQuantity = stockQuantity;
    }

    //Setters
    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public void setStockQuantity(int stockQuantity)
    {
        this.stockQuantity = stockQuantity;
    }

    //Getters
    public int getProductID()
    {
        return productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public double getPrice()
    {
        return price;
    }

    public Category getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public int getStockQuantity()
    {
        return stockQuantity;
    }
}
