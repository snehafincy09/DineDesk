package com.example.dinedesk;

public class FoodItem {
    public int id;
    public String name;
    public double price;
    public boolean available;
    public FoodItem(int id, String name, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }
}
