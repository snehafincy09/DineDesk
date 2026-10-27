package com.example.dinedesk;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class OrderHistoryActivity extends Activity {

    private LinearLayout orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.rgb(255, 248, 238));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 24, 20, 24);
        scrollView.addView(root);

        TextView heading = new TextView(this);
        heading.setText("MY ORDER HISTORY");
        heading.setTextSize(24);
        heading.setTextColor(Color.rgb(112, 25, 39));
        heading.setGravity(Gravity.CENTER);
        heading.setPadding(0, 0, 0, 20);
        root.addView(heading);

        orderList = new LinearLayout(this);
        orderList.setOrientation(LinearLayout.VERTICAL);
        root.addView(orderList);

        setContentView(scrollView);
        displayOrders();
    }

    private void displayOrders() {
        orderList.removeAllViews();

        boolean found = false;

        for (int i = OrderStore.orders.size() - 1; i >= 0; i--) {
            OrderStore.OrderRecord order = OrderStore.orders.get(i);

            TextView card = new TextView(this);
            StringBuilder details = new StringBuilder();

            details.append("Token: ").append(order.token).append("\n");
            details.append("Customer: ").append(order.customer).append("\n");
            details.append("Status: ").append(order.status).append("\n");
            details.append("Pickup: ").append(order.pickup).append("\n");
            details.append("Option: ").append(order.option).append("\n\n");

            for (String item : order.items) {
                details.append(item).append("\n");
            }

            details.append(String.format(
                    Locale.getDefault(),
                    "\nSubtotal: ₹%.2f\nGST (5%%): ₹%.2f"
                            + "\nGrand Total: ₹%.2f",
                    order.subtotal,
                    order.subtotal * 0.05,
                    order.getGrandTotal()
            ));

            card.setText(details.toString());
            card.setTextSize(16);
            card.setTextColor(Color.rgb(65, 40, 35));
            card.setPadding(18, 18, 18, 18);
            card.setBackgroundColor(Color.WHITE);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 0, 0, 16);
            orderList.addView(card, params);

            found = true;
        }

        if (!found) {
            TextView empty = new TextView(this);
            empty.setText(
                    "No orders yet.\nPlace your first order from the menu!");
            empty.setTextSize(17);
            empty.setTextColor(Color.DKGRAY);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(10, 40, 10, 40);
            orderList.addView(empty);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (orderList != null) {
            displayOrders();
        }
    }
}
