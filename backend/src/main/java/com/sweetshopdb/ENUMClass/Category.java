/**
 * This class represents the ENUM for product categories
 * 
 * @author 444 Team Alfredo Catzin
 * @version 1.0
 */
package com.sweetshopdb.ENUMClass;


public enum Category {
    
    GUMMIES("Gummies"),
    CHOCOLATE("Chocolate"),
    OTHER("Other");

    private final String categoryName;

    Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

}
