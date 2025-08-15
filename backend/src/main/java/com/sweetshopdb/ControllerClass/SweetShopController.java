/**
 * This class represents the controller class between the front and backend code. It uses HTTP
 * requests to communicate with the service classes and web pages.
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.ControllerClass;

import java.util.List;
import java.util.Locale;


//spring boot imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sweetshopdb.ENUMClass.Category;
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

    @Autowired 
    private ReviewService reviewService;

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

    //search endpoint to get products by category tab
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

    //endpoint to search for product by id
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
    try 
    {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    } catch (IllegalArgumentException e) 
    {
        return ResponseEntity.notFound().build();
    } catch (Exception e) 
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    //endpoint to search for products by category name from search bar
    @GetMapping("/products/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName)
    {
        return productService.searchByCategory(categoryName);
    }

    //endpoints for user login
    @PostMapping("/login")
    public String login(@RequestBody User user) throws DataAccessException
    {
        return userService.loginUser(user);
    }

    //endpoints for Phase 3 part 1 
    @GetMapping("/reports")
    public ResponseEntity<List<?>> getReports(@RequestParam(required = false) String action, @RequestParam(required = false) String date, @RequestParam(required = false) String catX, @RequestParam(required = false) String catY, @RequestParam(required = false) String username)
    {
        try 
        {
            switch(action)
            {
                case "most-expensive-per-category":
                    List<Product> categoryProducts = productService.getMostExpensiveFromAllCategories();
                    return ResponseEntity.ok(categoryProducts);

                case "same-day-xy":
                    Category categoryX = Category.valueOf(catX.toUpperCase(Locale.ROOT));
                    Category categoryY = Category.valueOf(catY.toUpperCase(Locale.ROOT));
                    List<String> products = productService.findSameDayXY(categoryX, categoryY);
                    return ResponseEntity.ok(products);

                case "good-or-better-by-userx":
                    List<Product> goodOrBetterProducts = reviewService.findGoodOrBetterByUserX(username);
                    return ResponseEntity.ok(goodOrBetterProducts);

                case "most-on-date":
                    List<String> usersWithMostPostsOnDate = productService.findUsersWithMostPostsOnDate(date);
                    return ResponseEntity.ok(usersWithMostPostsOnDate);

                case "all-poor": 
                    List<String> onlyPoorReviews = reviewService.findOnlyPoorReviews();
                    return ResponseEntity.ok(onlyPoorReviews);

                case "no-poor-or-none":
                    List<String> excludePoorReviews = reviewService.findExcludePoorReviews();
                    return ResponseEntity.ok(excludePoorReviews);

                default:
                    return ResponseEntity.ok(List.of());
            }
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}