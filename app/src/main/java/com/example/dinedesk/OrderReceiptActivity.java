package com.example.dinedesk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderReceiptActivity extends AppCompatActivity {

    private static final double GST_RATE = 0.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_receipt);

        TextView tvToken = findViewById(R.id.tvReceiptToken);
        TextView tvStatus = findViewById(R.id.tvReceiptStatus);
        TextView tvCustomer = findViewById(R.id.tvReceiptCustomer);
        TextView tvTime = findViewById(R.id.tvReceiptTime);
        TextView tvPickup = findViewById(R.id.tvReceiptPickup);
        TextView tvOption = findViewById(R.id.tvReceiptOption);
        TextView tvItems = findViewById(R.id.tvReceiptItems);
        TextView tvSubtotal = findViewById(R.id.tvReceiptSubtotal);
        TextView tvGst = findViewById(R.id.tvReceiptGst);
        TextView tvGrandTotal = findViewById(R.id.tvReceiptGrandTotal);
        Button btnClose = findViewById(R.id.btnCloseReceipt);

        String token = getIntent().getStringExtra("token");
        String customer = getIntent().getStringExtra("customer");
        String pickup = getIntent().getStringExtra("pickup");
        String option = getIntent().getStringExtra("option");
        String status = getIntent().getStringExtra("status");

        if (token == null || token.isEmpty()) {
            token = "DD" + (System.currentTimeMillis() % 1000000);
        }

        if (customer == null || customer.isEmpty()) {
            customer = "Student";
        }

        if (pickup == null || pickup.isEmpty()) {
            pickup = "Not selected";
        }

        if (option == null || option.isEmpty()) {
            option = "Takeaway";
        }

        if (status == null || status.isEmpty()) {
            status = "Pending";
        }

        tvToken.setText("#" + token);
        tvStatus.setText(status.toUpperCase(Locale.getDefault()));
        tvCustomer.setText("Customer: " + customer);
        tvPickup.setText("Pickup slot: " + pickup);
        tvOption.setText("Order option: " + option);

        String orderTime = new SimpleDateFormat(
                "dd MMM yyyy, hh:mm a",
                Locale.getDefault()
        ).format(new Date());

        tvTime.setText("Order time: " + orderTime);

        ArrayList<String> items =
                getIntent().getStringArrayListExtra("items");

        StringBuilder itemText = new StringBuilder();
        double subtotal = 0.0;

        if (items != null && !items.isEmpty()) {
            for (String item : items) {
                itemText.append(item).append("\n");
            }
        } else {
            itemText.append("Order details were not supplied.\n");
        }

        // The checkout screen passes the subtotal separately.
        subtotal = getIntent().getDoubleExtra("subtotal", 0.0);

        double gst = Math.round(subtotal * GST_RATE * 100.0) / 100.0;
        double grandTotal = subtotal + gst;

        tvItems.setText(itemText.toString().trim());
        tvSubtotal.setText(formatRupees(subtotal));
        tvGst.setText(formatRupees(gst));
        tvGrandTotal.setText(formatRupees(grandTotal));

        btnClose.setOnClickListener(v -> finish());
    }

    private String formatRupees(double amount) {
        return String.format(
                Locale.getDefault(),
                "₹%.2f",
                amount
        );
    }
}

