/**
 * This class serves as the interface that will conduct CRUD operations for the Review entity.
 * 
 * @see ReviewService.java, Review.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.RepositoryClass;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sweetshopdb.EntityClass.Product;
import com.sweetshopdb.EntityClass.Review;
import com.sweetshopdb.EntityClass.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>
{
        List<Review> findByUsername(String username);
        List<Review> findByProductID(int productId);

        @Query("SELECT COUNT(r) FROM Review r WHERE r.username = :username AND r.datePosted = :date_posted")
        int userReviewLimitPerDay(@Param("username") String username, @Param("date_posted") LocalDate date_posted);

        boolean existsByUsernameAndProductID(@Param("username") String username, @Param("productId") int productId);

        //custom query (Phase 3 pt3)
        @Query("SELECT DISTINCT p FROM Product p WHERE p.addedByUsername = :username " +
                "AND p.productID IN (SELECT DISTINCT r1.productID FROM Review r1 WHERE r1.productID = p.productID) " +
                "AND p.productID NOT IN " +
                "(SELECT DISTINCT r2.productID FROM Review r2 WHERE r2.productID = p.productID " +
                "AND (r2.rating = 'POOR' OR r2.rating = 'FAIR'))")
        List<Product> findGoodOrBetterByUserX(@Param("username") String username);

        //custom query (pt5)
        @Query("SELECT DISTINCT r.username FROM Review r " +
                "WHERE r.username NOT IN " +
                "(SELECT r2.username FROM Review r2 WHERE r2.rating != 'POOR')")
        List<String> findOnlyPoorReviews();

        //custom query (pt6)
        @Query("SELECT DISTINCT p.addedByUsername FROM Product p " +
                "WHERE p.addedByUsername NOT IN " +
                "(SELECT DISTINCT p2.addedByUsername FROM Product p2 " +
                "JOIN Review r ON p2.productID = r.productID WHERE r.rating = 'POOR')")
        List<String> findExcludePoorReviews();
}
