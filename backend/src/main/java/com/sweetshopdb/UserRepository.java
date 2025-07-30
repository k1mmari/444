/**
 * This class serves as an interface for user-related database operations and methods.
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<user, String>
{
    //additional custom queries can be defined here, built in methods are already provided by JpaRepository
    public abstract boolean existsByUsername(String username);
    public abstract boolean existsByEmail(String email);
}