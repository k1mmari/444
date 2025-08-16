/**
 * This class serves as the service layer for the Review functionality 
 * and contains the business logic for managing reviews.
 * 
 * @see ReviewRepository.java, Review.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ServiceClass;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sweetshopdb.ENUMClass.Rating;
import com.sweetshopdb.EntityClass.Product;
import com.sweetshopdb.RepositoryClass.ReviewRepository;
import com.sweetshopdb.EntityClass.Review;

@Service
public class ReviewService 
{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired 
    private ProductService productService;

    public Review leaveReview(String username, int productID, Rating rating, String reviewDescription)
    {
        LocalDate today = LocalDate.now();
        int reviewsLeftToday = reviewRepository.userReviewLimitPerDay(username, today);

        boolean userExists = userService.existsByUsername(username);
        if(!userExists)
        {
            throw new IllegalArgumentException("Username does not exist. Please sign up first and try again.");
        }

        Product product = productService.getProductById(productID);
        if(product == null)
        {
            throw new IllegalArgumentException("Product not found.");
        }

        if(product.getAddedByUsername().equals(username))
        {
            throw new IllegalArgumentException("User cannot review their own product.");
        }

        if (reviewRepository.existsByUsernameAndProductID(username, productID)) 
        {
            throw new IllegalStateException("User has already reviewed this product.");
        }
        if(reviewsLeftToday >= 2)
        {
            throw new IllegalStateException("User: " + username + " has reached the review limit for today.");
        }

        Review savedReview = new Review(username, productID, rating, reviewDescription, today);
        System.out.println("ReviewService: Saving review for user: " + username + " on product ID: " + productID);
        return reviewRepository.save(savedReview);
    }

    //method to find products with good or excellent reviews by a specific user
    public List<Review> findGoodOrBetterByUserX(String username)
    {
        if(username == null || username.trim().isEmpty())
        {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if(!userService.existsByUsername(username))
        {
            throw new IllegalArgumentException("Username does not exist. Please sign up first and try again.");
        }

        System.out.println("Review Service: Finding good or excellent reviews for user: " + username);
        return reviewRepository.findGoodOrBetterByUserX(username);
    }

    //method to find users who left only poor reviews
    public List<Review> findOnlyPoorReviews()
    {
        return reviewRepository.findOnlyPoorReviews();
    }

    //fill in logic
    public List<Review> findExcludePoorReviews()
    {
        return reviewRepository.findExcludePoorReviews();
    }

    //helper methods
    public List<Review> getReviewsByUser(String username) 
    {
        if (username == null || username.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return reviewRepository.findByUsername(username);
    }

    public List<Review> getReviewsForProduct(int productId) 
    {
        if (productId <= 0) 
        {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        return reviewRepository.findByProductID(productId);
    }
}

