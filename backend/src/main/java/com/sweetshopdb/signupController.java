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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class signupController 
{
    @Autowired //injects the jdbc bean into the controller class- 
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/signup")
    public String signUp(@RequestBody user user) throws DataAccessException
    {
        if(!matchingPasswords(user))
        {
            return "Error! The passwords do not match.";
        }

        if(usernameExists(user.getUsername()))
        {
            return "Error! This username has already been taken.";
        }

        if(emailExists(user.getEmail()))
        {
            return "Error! This email address has already been used.";
        }

        String sql = "INSERT INTO user (username, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), 
            user.getEmail());
            return "Signup successful! " + user.getFirstName() + " " + user.getLastName() + " has been added.";
    }

    private boolean matchingPasswords(user user)
    {
        return user.getPassword().equals(user.getConfirmPassword());
    }

    private boolean usernameExists(String username)
    {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM user WHERE username = ?", Integer.class, username);
            return count != null && count > 0;
    }

    private boolean emailExists(String email)
    {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM user WHERE email = ?", Integer.class, email);
            return count != null && count > 0;
    }
}