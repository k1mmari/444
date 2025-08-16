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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    //Method to find product by ID
    public Product getProductById(int productId) 
    {
    Optional<Product> product = productRepository.findById(productId);
    if (product.isPresent()) 
    {
        return product.get();
    } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    //method to find the most expensive item in each category
    public List<Product> getMostExpensiveFromAllCategories()
    {
        List<Product> result = new ArrayList<>();

        try
        {
            for(Category category : Category.values())
            {
                List<Product> categoryProducts = productRepository.getMostExpensiveCategoryList(category);
                if(!categoryProducts.isEmpty())
                {
                    result.add(categoryProducts.get(0));
                }
            }
        }
        catch(Exception e) 
        {
            System.err.println("Error occurred while fetching most expensive products: " + e.getMessage());
            return List.of();
        }

        System.out.println("ProductService: Found " + result.size() + " products");
        return result;
    }

    /*Method to find users who posted items in particular categories (specified by the user) on the same day */
    public List<Product> findSameDayXY(Category catX, Category catY)
    {
        if(catX == null || catY == null)
        {
            System.out.println("Category names cannot be null.");
            return List.of();
        }

        if(catX.equals(catY))
        {
            System.out.println("Category names must be different.");
            return List.of();
        }

        if(!productRepository.existsByCategory(catX) || !productRepository.existsByCategory(catY))
        {
            System.out.println("One or both categories do not exist.");
            return List.of();
        }
        //output for debugging
        System.out.println("Searching for users who posted items in categories: " + catX + " and " + catY);

        return productRepository.findSameDayXY(catX, catY);
    }

    public List<Product> findUsersWithMostPostsOnDate(String dateString)
    {
        if(dateString == null || dateString.trim().isEmpty())
        {
            System.out.println("Date cannot be null or empty.");
            return List.of();
        }

        try
        {
            LocalDate date = LocalDate.parse(dateString);
            List<Product> result = productRepository.findUsersWithMostPostsOnDate(date);
            System.out.println("ProductService: Found " + result.size() + " products.");
            return result;
        }
        catch(Exception e)
        {
            System.out.println("Invalid date format. Expected format is: YYYY-MM-DD");
            return List.of();
        }
    }
}
