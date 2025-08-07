/**
 * This file represents the repository of methods for product-related database operations. It serves as an interface
 * for managing product data logic, including CRUD operations and custom queries.
 * 
 * @see Product.java (entity)
 * @author 444 Team Alfredo
 * @version 1.0
 */

package com.sweetshopdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> 
{
    List<Product> findByCategory(Category category);
    List<Product> findByProductNameContainingIgnoreCase(String productName);

}
