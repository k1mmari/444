/**
 * This class represents the controller class between the front and backend code. It uses HTTP
 * requests to communicate with the service classes and web pages.
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ControllerClass;

import java.util.List;
import java.util.Optional;

//spring boot imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sweetshopdb.EntityClass.*;
import com.sweetshopdb.ServiceClass.*;


@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RequestMapping("/api") 
public class SweetShopController 
{
    @Autowired //injects and initializes the UserService bean
    private UserService userService;

    @Autowired //injects and initializes the ProductService bean
    private ProductService productService;

    //End point for user signup.html
    @PostMapping("/signup")
    public String signUp(@RequestBody User user) throws DataAccessException
    {
        return userService.registerUser(user);
    }

    //search endpoint for search.html
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q)
    {
        System.out.println("Search request received for: " + q); //test output
        return productService.searchProducts(q);
    }

    @GetMapping("/search/category/{categoryName}")
    public List<Product> searchByCategory(@PathVariable String categoryName)
    {
        return productService.searchByCategory(categoryName);
    }

    //add product endpoint
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product, @RequestParam String username) throws DataAccessException
    {
        return productService.addProduct(product, username);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        try
        {
            Optional<Product> product = productService.findById(id);
            if(product.isPresent())
            {
                return ResponseEntity.ok(product.get());
            }
            else
            {
                return ResponseEntity.notFound().build();
            }
        }   catch(Exception e)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @GetMapping("/products/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName)
    {
        return productService.searchByCategory(categoryName);
    }

}