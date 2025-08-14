/**
 * This class represents the review entity in the SweetShop database and contains the objects attributes and methods
 * in order to access the review data.
 * 
 * @see ReviewRepository.java, ReviewService.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.EntityClass;

import java.time.LocalDate;
import jakarta.persistence.*;
import com.sweetshopdb.ENUMClass.Rating;

@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "prod_id"}))
public class Review 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewID;

    private String username;

    @Column(name = "prod_id")
    private int productID;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "review_description")
    private String reviewDescription;

    @Column(name = "date_posted")
    private LocalDate datePosted;

    //No arg constructor
    public Review() {}

    public Review(String username, int productID, Rating rating, String reviewDescription, LocalDate datePosted)
    {
        this.username = username;
        this.productID = productID;
        this.rating = rating;
        this.reviewDescription = reviewDescription;
        this.datePosted = datePosted;
    }

    //setters
    public void setReviewID(int reviewID)
    {
        this.reviewID = reviewID;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public void setRating(Rating rating)
    {
        this.rating = rating;
    }

    public void setReviewDescription(String reviewDescription)
    {
        this.reviewDescription = reviewDescription;
    }

    public void setDatePosted(LocalDate datePosted)
    {
        this.datePosted = datePosted;
    }

    //getters
    public int getReviewID()
    {
        return reviewID;
    }

    public String getUsername()
    {
        return username;
    }

    public int getProductID()
    {
        return productID;
    }

    public Rating getRating()
    {
        return rating;
    }

    public String getReviewDescription()
    {
        return reviewDescription;
    }

    public LocalDate getDatePosted()
    {
        return datePosted;
    }

}
