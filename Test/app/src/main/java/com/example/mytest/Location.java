package com.example.mytest;

public class Location
{
    private String locationName;
    private String x;
    private String y;

    public Location(String locationName, String x, String y)
    {
        this.locationName = locationName;
        this.x = x;
        this.y = y;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public String getX()
    {
        return x;
    }

    public String getY()
    {
        return y;
    }
}
