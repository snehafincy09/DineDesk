package com.example.dinedesk;

public class CartItem {
    public FoodItem foodItem;
    public int quantity;
    public CartItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }
    public double getSubtotal() {
        return foodItem.price * quantity;
    }
}
