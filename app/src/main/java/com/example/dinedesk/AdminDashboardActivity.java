package com.example.dinedesk;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class AdminDashboardActivity extends Activity {
    private LinearLayout orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.rgb(255, 248, 238));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);
        scrollView.addView(root);

        TextView heading = new TextView(this);
        heading.setText("DINEDESK ADMIN DASHBOARD");
        heading.setTextSize(23);
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

        if (OrderStore.orders.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No orders have been placed yet.");
            empty.setTextSize(17);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(10, 40, 10, 40);
            orderList.addView(empty);
            return;
        }

        for (int i = OrderStore.orders.size() - 1; i >= 0; i--) {
            OrderStore.OrderRecord order = OrderStore.orders.get(i);

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(18, 18, 18, 18);
            card.setBackgroundColor(Color.WHITE);

            TextView details = new TextView(this);
            StringBuilder text = new StringBuilder();

            text.append("Token: ").append(order.token).append("\n");
            text.append("Customer: ").append(order.customer).append("\n");
            text.append("Status: ").append(order.status).append("\n");
            text.append("Pickup: ").append(order.pickup).append("\n");
            text.append("Order Type: ").append(order.option).append("\n\n");

            for (String item : order.items) {
                text.append(item).append("\n");
            }

            double gst = Math.round(order.subtotal * 0.05 * 100.0) / 100.0;

            text.append(String.format(
                    Locale.getDefault(),
                    "\nSubtotal: ₹%.2f\nGST (5%%): ₹%.2f\nGrand Total: ₹%.2f",
                    order.subtotal,
                    gst,
                    order.subtotal + gst
            ));

            details.setText(text.toString());
            details.setTextSize(16);
            details.setTextColor(Color.rgb(65, 40, 35));
            card.addView(details);

            Button statusButton = new Button(this);
            statusButton.setText("Update Order Status");
            statusButton.setOnClickListener(v -> showStatusOptions(order));
            card.addView(statusButton);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 20);

            orderList.addView(card, params);
        }
    }

    private void showStatusOptions(OrderStore.OrderRecord order) {
        String[] statuses = {
                "Pending",
                "Preparing",
                "Ready",
                "Completed"
        };

        new AlertDialog.Builder(this)
                .setTitle("Update status for " + order.token)
                .setItems(statuses, (dialog, which) -> {
                    order.status = statuses[which];

                    Toast.makeText(
                            this,
                            "Order status updated to " + order.status,
                            Toast.LENGTH_SHORT
                    ).show();

                    displayOrders();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (orderList != null) {
            displayOrders();
        }
    }
}

