/**
 * This class serves as an interface for user-related database operations and methods.
 * 
 * @see User.java
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb.RepositoryClass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sweetshopdb.EntityClass.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    //additional custom queries can be defined here, built in methods are already provided by JpaRepository
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
}