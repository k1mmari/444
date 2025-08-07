/**
 * This file represents the repository of methods for product-related database operations. It
 * serves as an interface for managing product data logic, including CRUD operations and custom
 * queries.
 * 
 * @see Product.java (entity)
 * @author 444 Team Alfredo
 * @version 1.0
 */

package com.sweetshopdb.RepositoryClass;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sweetshopdb.ENUMClass.Category;
import com.sweetshopdb.EntityClass.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> 
{
    List<Product> findByCategory(Category category);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> searchByCategory(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE CONCAT('%', :searchTerm, '%')")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProductsCaseInsensitive(@Param("searchTerm") String searchTerm);
}
