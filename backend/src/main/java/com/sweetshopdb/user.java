/**
 * This class represent a user object in the backend code and contains the proper setters/getters and important attributes of the user
 * 
 * @author 444 Team Alfredo Catzin
 * @version 1.0
 */

package com.sweetshopdb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class user 
{
    @Id
    private String username;

    private String password;
    private String firstName;
    private String lastName;
    private String email;

    //no arg constructor
    public user() {}
    //constructor
    public user(String username, String password, String firstName, String lastName, String email)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //setters
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    //getters
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }
}
