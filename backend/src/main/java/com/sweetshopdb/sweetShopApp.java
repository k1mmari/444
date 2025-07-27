/**
 * This class serves as the starting point for the backend and allows for communication 
 * with the database
 * 
 * @author 444 Team Alfredo
 * @version 1.0
 */

package com.sweetshopdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class sweetShopApp 
{
    public static void main(String[] args)
    {
        SpringApplication.run(sweetShopApp.class);
    }
}
