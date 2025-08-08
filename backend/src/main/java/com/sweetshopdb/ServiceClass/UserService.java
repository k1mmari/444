/**
 * This java files is responsible for handling the user-related business logic
 * 
 * @see SweetShopController, UserRepository, User
 * @author 444 Team Alfredo
 * @version 1.0
 */

package com.sweetshopdb.ServiceClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sweetshopdb.RepositoryClass.UserRepository;
import com.sweetshopdb.EntityClass.User;

@Service
public class UserService 
{
    @Autowired //Injects and initializes the UserRepository bean
    private UserRepository userRepository;

    public String registerUser(User user)
    {
        if(!user.matchPasswords(user.getPassword(), user.getConfirmPassword()))
        {
            return "Error! The passwords do not match.";
        }

        if(userRepository.existsByUsername(user.getUsername()))
        {
            return "Error! This username has already been taken.";
        }

        if(userRepository.existsByEmail(user.getEmail()))
        {
            return "Error! This email address has already been used.";
        }

            userRepository.save(user);
            return "Signup successful! " + user.getFirstName() + " " + user.getLastName() + " has been added.";
    }

    public boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }
}
