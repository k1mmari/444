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

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sweetshopdb.ENUMClass.Category;
import com.sweetshopdb.EntityClass.Product;
import com.sweetshopdb.EntityClass.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> 
{
        List<Product> findByCategory(Category category);
        List<Product> findByProductNameContainingIgnoreCase(String productName);
        List<Product> searchByCategory(String categoryName);
        boolean existsByCategory(Category category);

        //Custom query (Phase 3 pt1) to select the most expensive products from each category
        @Query("SELECT p FROM Product p WHERE p.category = :category ORDER BY p.price DESC")
        List<Product> getMostExpensiveCategoryList(Category category);

        //custom query (Phase 3 pt2)
        @Query("SELECT DISTINCT p1.addedByUsername " +
                "FROM Product p1 " +
                "WHERE EXISTS (SELECT 1 FROM Product p2 " +
                "WHERE p1.addedByUsername = p2.addedByUsername " +
                "AND p1.category = :catX AND p2.category = :catY " +
                "OR (p1.category = :catY AND p2.category = :catX) " +
                "AND p1.dateAdded = p2.dateAdded " +
                "AND p1.productID != p2.productID)")
        List<String> findSameDayXY(@Param("catX") Category catX, @Param("catY") Category catY);

        //custom query (pt4) goes here
        @Query("SELECT p.addedByUsername FROM Product p WHERE DATE(p.dateAdded) = :date " +
                "GROUP BY p.addedByUsername " +
                "HAVING COUNT(p.productID) = " +
                "(SELECT MAX(cnt) FROM " +
                "(SELECT COUNT(p2.productID) as cnt FROM Product p2 WHERE DATE(p2.dateAdded) = :date GROUP BY p2.addedByUsername))")
        List<String> findUsersWithMostPostsOnDate(String date);

        @Query("SELECT p " +
                "FROM Product p " +
                "WHERE p.productName LIKE CONCAT('%', :searchTerm, '%')")
        List<Product> searchProducts(@Param("searchTerm") String searchTerm);

        @Query("SELECT p " +
                "FROM Product p " +
                "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        List<Product> searchProductsCaseInsensitive(@Param("searchTerm") String searchTerm);

        @Query("SELECT COUNT(p) " +
                "FROM Product p " +
                "WHERE p.addedByUsername = :username AND p.dateAdded = :dateAdded")
        int userAddLimitPerDay(@Param("username") String username, @Param("dateAdded") LocalDate dateAdded);
        }
