package com.sweetshopdb.ENUMClass;

public enum Rating 
{
    EXCELLENT("Excellent"),
    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor");

    private final String rating;

    Rating(String rating)
    {
        this.rating = rating;
    }

    public String getRating()
    {
        return rating;
    }
}
