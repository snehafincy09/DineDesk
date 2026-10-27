package com.example.dinedesk;

import java.util.ArrayList;

public class OrderStore {

    public static class OrderRecord {
        public String token;
        public String customer;
        public String pickup;
        public String option;
        public String status;
        public ArrayList<String> items;
        public double subtotal;

        public OrderRecord(
                String token,
                String customer,
                String pickup,
                String option,
                String status,
                ArrayList<String> items,
                double subtotal) {

            this.token = token;
            this.customer = customer;
            this.pickup = pickup;
            this.option = option;
            this.status = status;
            this.items = new ArrayList<>(items);
            this.subtotal = subtotal;
        }

        public double getGrandTotal() {
            return subtotal + (subtotal * 0.05);
        }
    }

    public static final ArrayList<OrderRecord> orders =
            new ArrayList<>();
}
