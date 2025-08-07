/**
 * This class provides the business logic for product-related operations.
 * 
 * @see Product.java, ProductRepository.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ServiceClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sweetshopdb.EntityClass.Product;
import com.sweetshopdb.RepositoryClass.ProductRepository;
import java.util.List;

@Service
public class ProductService 
{
    @Autowired //injects and initializes the ProductRepository bean
    private ProductRepository productRepository;

    //method to return a search result for products via user queries
    public List<Product> searchProducts(String query)
    {
        if(query == null || query.trim().isEmpty())
        {
            return productRepository.findAll(); // Return all products if query is empty
        }

        return productRepository.findByProductNameContainingIgnoreCase(query.trim()); // Return products matching the query
    }

    //method for a user to add a product to the DB
    public Product addProduct(Product product)
    {
        //checks consistency constraints before adding new product
        if(product.getProductName() == null 
            || product.getProductID() <= 0 
            || product.getPrice() <= 0 
            || product.getCategory() == null 
            || product.getStockQuantity() < 0)
        {
            throw new IllegalArgumentException("Invalid product details. Please try again."); 
        }
        return productRepository.save(product);
    }
}
