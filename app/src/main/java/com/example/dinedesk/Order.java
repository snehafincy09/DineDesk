package com.example.dinedesk;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public int orderId;
    public String username;
    public List<CartItem> items;
    public double total;
    public String status;

    public Order(int orderId, String username,
                 List<CartItem> items, double total, String status) {
        this.orderId = orderId;
        this.username = username;
        this.items = new ArrayList<>(items);
        this.total = total;
        this.status = status;
    }
}

