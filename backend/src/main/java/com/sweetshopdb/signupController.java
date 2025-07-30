/**
 * This class represents the backend functionality for the signup page and contains methods
 * to add a registered user along with error handling to ensure unique emails and 
 * corresponding passwords
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

package com.sweetshopdb;

//spring boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class signupController 
{
    @Autowired //injects and initializes the UserRepository bean
    private UserRepository userRepository;

    @PostMapping("/signup")
    public String signUp(@RequestBody user user) throws DataAccessException
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

}