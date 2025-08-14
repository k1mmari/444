/**
 * This class serves as the interface that will conduct CRUD operations for the Review entity.
 * 
 * @see ReviewService.java, Review.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.RepositoryClass;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sweetshopdb.EntityClass.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>
{
    @Query("SELECT COUNT(r) FROM Review r WHERE r.username = :username AND r.datePosted = :date_posted")
    int userReviewLimitPerDay(@Param("username") String username, @Param("date_posted") LocalDate date_posted);
    
    boolean existsByUsernameAndProductID(@Param("username") String username, @Param("productId") int productId);


}
