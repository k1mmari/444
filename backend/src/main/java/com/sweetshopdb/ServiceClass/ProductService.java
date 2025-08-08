/**
 * This class provides the business logic for product-related operations.
 * 
 * @see Product.java, ProductRepository.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ServiceClass;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sweetshopdb.EntityClass.Product;
import com.sweetshopdb.RepositoryClass.ProductRepository;
import java.util.List;
import com.sweetshopdb.ENUMClass.Category;

@Service
public class ProductService 
{
    @Autowired //injects and initializes the ProductRepository bean
    private ProductRepository productRepository;

    @Autowired //injects and initializes the UserService bean
    private UserService userService;

    //method to return a search result for product name's (kit-kat)
    public List<Product> searchProducts(String query)
    {
        System.out.println("ProductService: Searching for: " + query); //test output
        if(query == null || query.trim().isEmpty())
        {
            List<Product> allProducts = productRepository.findAll();
            System.out.println("ProductService: Found " + allProducts.size() + " products (all)");
            return allProducts;
        }

        List<Product> findProducts = productRepository.searchProductsCaseInsensitive(query.trim());
        System.out.println("ProductService: Found " + findProducts.size() + " products for query: " + query);
        return findProducts;
    }

    //categorical search via categories tab
    public List<Product> searchByCategory(String categoryName) throws IllegalArgumentException
    {
        System.out.println("ProductService: Searching for category: " + categoryName);

        try 
        {
            Category category = Category.valueOf(categoryName.trim().toUpperCase());
            List<Product> categoryProducts = productRepository.findByCategory(category);
            System.out.println("ProductService: Found " + categoryProducts.size() + " products in category: " + categoryName);
            return categoryProducts;
        } catch(IllegalArgumentException e)
        {
            System.out.println("ProductService: Invalid category name: " + categoryName);
            return List.of();
        }
    }

    //method for a user to add a product to the DB
    public Product addProduct(Product product, String username)
    {
        LocalDate today = LocalDate.now();
        int productsAddedToday = productRepository.userAddLimitPerDay(username, today);

        if(productsAddedToday >= 2)
        {
            throw new IllegalArgumentException("User has reached the daily limit of 2 products.");
        }
        //checks consistency constraints before adding new product
        if(product.getProductName() == null || product.getProductName().trim().isEmpty()
            || product.getPrice() <= 0 
            || product.getCategory() == null)
        {
            throw new IllegalArgumentException("Invalid product details. Please try again."); 
        }

        boolean userExists = userService.existsByUsername(username);
        if(!userExists)
        {
            throw new IllegalArgumentException("Username does not exist. Please sign up first and try again.");
        }

        if(product.getStockQuantity() <= 0) {
        product.setStockQuantity(1);
    }

        product.setAddedByUsername(username);
        product.setDateAdded(today);

        System.out.println("ProductService: Adding product: " + product.getProductName() + " by user: " + username + " on " + today);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }
    
}
