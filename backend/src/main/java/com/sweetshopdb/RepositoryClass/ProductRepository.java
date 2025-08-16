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


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> 
{
        //helper queries (Phase 3 pt2)
        List<Product> findByCategory(Category category);
        boolean existsByCategory(Category category);

        //Custom query (Phase 2 pt2) to perform a case-insensitive search via the search bar
        @Query("SELECT p " +
                "FROM Product p " +
                "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        List<Product> searchProductsCaseInsensitive(@Param("searchTerm") String searchTerm);

        //Custom query (Phase 3 pt1) to select the most expensive products from each category
        @Query("SELECT p FROM Product p WHERE p.category = :category ORDER BY p.price DESC")
        List<Product> getMostExpensiveCategoryList(Category category);

        //custom query (Phase 3 pt2)
        @Query("SELECT p FROM Product p " +
        "WHERE :catX != :catY " + 
        "AND EXISTS (" +
        "    SELECT 1 FROM Product p1 " +
        "    WHERE p1.addedByUsername = p.addedByUsername " +
        "    AND p1.category = :catX " +
        "    AND DATE(p1.dateAdded) = DATE(p.dateAdded)" +
        ") " +
        "AND EXISTS (" +
        "    SELECT 1 FROM Product p2 " +
        "    WHERE p2.addedByUsername = p.addedByUsername " +
        "    AND p2.category = :catY " +
        "    AND DATE(p2.dateAdded) = DATE(p.dateAdded)" +
        ") " +
        "ORDER BY p.addedByUsername, p.dateAdded, p.category")
        List<Product> findSameDayXY(@Param("catX") Category catX, @Param("catY") Category catY);

        //custom query (pt4) goes here
        @Query("SELECT p FROM Product p " +
        "WHERE p.dateAdded = :date " +
        "AND p.addedByUsername IN (" +
        "    SELECT p1.addedByUsername FROM Product p1 " +
        "    WHERE p1.dateAdded = :date " +
        "    GROUP BY p1.addedByUsername " +
        "    HAVING COUNT(p1.productID) = 2" +
        ") " +
        "ORDER BY p.addedByUsername")
        List<Product> findUsersWithMostPostsOnDate(@Param("date") LocalDate date);

        @Query("SELECT COUNT(p) " +
                "FROM Product p " +
                "WHERE p.addedByUsername = :username AND p.dateAdded = :dateAdded")
        int userAddLimitPerDay(@Param("username") String username, @Param("dateAdded") LocalDate dateAdded);
        }
