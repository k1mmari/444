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
import com.sweetshopdb.EntityClass.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>
{
        List<Review> findByUsername(String username);
        List<Review> findByProductID(int productId);
        boolean existsByUsernameAndProductID(@Param("username") String username, @Param("productId") int productId);

        @Query("SELECT COUNT(r) FROM Review r WHERE r.username = :username AND r.datePosted = :datePosted")
        int userReviewLimitPerDay(@Param("username") String username, @Param("datePosted") LocalDate datePosted);

        //custom query (Phase 3 pt3) to find the products that have received good or excellent reviews
        @Query("SELECT r FROM Review r " +
        "JOIN Product p ON r.productID = p.productID " +
        "WHERE p.addedByUsername = :username " +
        "AND r.rating IN ('GOOD', 'EXCELLENT') " +
        "AND r.productID NOT IN (" +
        "    SELECT r2.productID FROM Review r2 " +
        "    WHERE r2.productID = r.productID " +
        "    AND r2.rating IN ('POOR', 'FAIR')" +
        ") " +
        "ORDER BY r.datePosted DESC")
        List<Review> findGoodOrBetterByUserX(@Param("username") String username);

        //custom query (Phase 3 pt5) to find users who have only given poor reviews
        @Query("SELECT r FROM Review r " +
        "WHERE r.username NOT IN (" +
        "    SELECT r2.username FROM Review r2 " +
        "    WHERE r2.rating != 'POOR'" +
        ") " +
        "AND r.rating = 'POOR' " + 
        "ORDER BY r.username, r.datePosted DESC")
        List<Review> findOnlyPoorReviews();

        //custom query (Phase 3 pt6) to find reviews for products added by users who have not received poor reviews
        @Query("SELECT r FROM Review r " +
                "WHERE r.productID IN (" +
                "    SELECT p.productID FROM Product p " +
                "    WHERE p.addedByUsername NOT IN (" +
                "        SELECT DISTINCT p2.addedByUsername FROM Product p2 " +
                "        WHERE p2.productID IN (" +
                "            SELECT r2.productID FROM Review r2 WHERE r2.rating = 'POOR'" +
                "        )" +
                "    )" +
                ") " +
                "ORDER BY r.username, r.datePosted DESC")
        List<Review> findExcludePoorReviews();
}
