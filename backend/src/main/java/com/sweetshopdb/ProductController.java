package com.sweetshopdb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend fetch requests
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("q") String query) {
        return productRepository.findByProductNameContainingIgnoreCase(query);
    }
}
