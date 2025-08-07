/**
 * This class represents the controller class between the front and backend code. It uses HTTP
 * requests to communicate with the service classes and web pages.
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ControllerClass;

import java.util.List;

//spring boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    //add product endpoint
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) throws DataAccessException
    {
        return productService.addProduct(product);
    }

}