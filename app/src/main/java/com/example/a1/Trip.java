package com.example.a1;

public class Trip
{
    public String name;
    public String destination;
    public String type;
    public String departureDate;
    public int budget;
    public String duration;

    public Trip(String name, String destination, String type, String departureDate, int budget, String duration)
    {
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.departureDate = departureDate;
        this.budget = budget;
        this.duration = duration;
    }
}
