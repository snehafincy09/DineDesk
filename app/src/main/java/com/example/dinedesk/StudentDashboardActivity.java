package com.example.dinedesk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private TextView tvCartCount;
    private TextView tvCartTotal;

    private int cartCount = 0;
    private double cartTotal = 0.0;

    private final Map<String, Integer> cartItems =
            new LinkedHashMap<>();

    private final Map<String, Double> foodPrices =
            new LinkedHashMap<>();

    private String username = "Student";
    private String pickupSlot = "12:30 PM - Lunch Break";
    private String orderOption = "Takeaway";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        findViewById(R.id.btnOrderHistory).setOnClickListener(v -> {
            startActivity(
                    new android.content.Intent(
                            StudentDashboardActivity.this,
                            OrderHistoryActivity.class
                    )
            );
        });

        tvWelcome = findViewById(R.id.tvWelcome);
        tvCartCount = findViewById(R.id.tvCartCount);
        tvCartTotal = findViewById(R.id.tvCartTotal);

        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> showCheckout());

        String receivedUsername =
                getIntent().getStringExtra("username");

        if (receivedUsername != null
                && !receivedUsername.isEmpty()) {
            username = receivedUsername;
        }

        tvWelcome.setText("Welcome, " + username + "!");

        setupFoodButton(R.id.btnAddCutlet, "Cutlet", 20.0);
        setupFoodButton(R.id.btnAddSamosa, "Samosa", 15.0);
        setupFoodButton(R.id.btnAddChickenPuffs,
                "Chicken Puffs", 25.0);
        setupFoodButton(R.id.btnAddMeatRoll, "Meat Roll", 25.0);
        setupFoodButton(R.id.btnAddBurger, "Burger", 60.0);

        setupFoodButton(R.id.btnAddChickenBiriyani,
                "Chicken Biriyani", 100.0);
        setupFoodButton(R.id.btnAddChickenFriedRice,
                "Chicken Fried Rice", 80.0);
        setupFoodButton(R.id.btnAddRice, "Veg Fried Rice", 70.0);
        setupFoodButton(R.id.btnAddMeals, "Meals", 50.0);

        setupFoodButton(R.id.btnAddTea, "Tea", 15.0);
        setupFoodButton(R.id.btnAddCoffee, "Coffee", 20.0);
        setupFoodButton(R.id.btnAddJuice, "Fresh Juice", 40.0);

        setupFoodButton(R.id.btnAddIceCream, "Ice Cream", 35.0);
        setupFoodButton(R.id.btnAddCake, "Cake", 40.0);
    }

    private void setupFoodButton(int buttonId,
                                 String foodName,
                                 double price) {

        Button button = findViewById(buttonId);

        foodPrices.put(foodName, price);

        button.setOnClickListener(
                v -> addToCart(foodName, price));
    }

    private void addToCart(String foodName, double price) {

        cartCount++;
        cartTotal += price;

        int quantity = cartItems.containsKey(foodName)
                ? cartItems.get(foodName) + 1
                : 1;

        cartItems.put(foodName, quantity);

        tvCartCount.setText("Cart: " + cartCount
                + (cartCount == 1 ? " item" : " items"));

        tvCartTotal.setText(String.format(
                Locale.getDefault(),
                "Total: ₹%.2f",
                cartTotal));

        Toast.makeText(this,
                foodName + " added to cart!",
                Toast.LENGTH_SHORT).show();
    }


    private void showCheckout() {

        if (cartCount == 0) {
            Toast.makeText(this,
                    "Your cart is empty. Add some food first!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder summary = new StringBuilder();

        for (Map.Entry<String, Integer> item
                : cartItems.entrySet()) {

            summary.append(item.getKey())
                    .append(" x ")
                    .append(item.getValue())
                    .append("\n");
        }

        double gst = Math.round(cartTotal * 0.05 * 100.0) / 100.0;
        double grandTotal = cartTotal + gst;

        summary.append("\nSubtotal: ")
                .append(String.format(Locale.getDefault(),
                        "₹%.2f", cartTotal));

        summary.append("\nGST (5%): ")
                .append(String.format(Locale.getDefault(),
                        "₹%.2f", gst));

        summary.append("\nGrand Total: ")
                .append(String.format(Locale.getDefault(),
                        "₹%.2f", grandTotal));

        // Create the pickup-time dropdown.
        android.widget.Spinner pickupSpinner =
                new android.widget.Spinner(this);

        String[] pickupTimes = {
                "11:05 AM - Second Break",
                "12:15 PM - Lunch Break",
                "3:20 PM - Fourth Break"
        };

        android.widget.ArrayAdapter<String> adapter =
                new android.widget.ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        pickupTimes);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        pickupSpinner.setAdapter(adapter);

        // Create Takeaway / Dine-In choices.
        android.widget.RadioGroup optionGroup =
                new android.widget.RadioGroup(this);

        optionGroup.setOrientation(
                android.widget.RadioGroup.VERTICAL);

        android.widget.RadioButton takeaway =
                new android.widget.RadioButton(this);
        takeaway.setText("Takeaway");
        takeaway.setId(android.view.View.generateViewId());

        android.widget.RadioButton dineIn =
                new android.widget.RadioButton(this);
        dineIn.setText("Dine-In");
        dineIn.setId(android.view.View.generateViewId());

        optionGroup.addView(takeaway);
        optionGroup.addView(dineIn);
        optionGroup.check(takeaway.getId());

        // Arrange checkout information and controls.
        android.widget.LinearLayout layout =
                new android.widget.LinearLayout(this);

        layout.setOrientation(
                android.widget.LinearLayout.VERTICAL);

        int padding = (int) (20 * getResources()
                .getDisplayMetrics().density);

        layout.setPadding(padding, padding, padding, padding);

        TextView summaryView = new TextView(this);
        summaryView.setText(summary.toString());
        summaryView.setTextSize(16);
        layout.addView(summaryView);

        TextView pickupLabel = new TextView(this);
        pickupLabel.setText("\nChoose Pickup Time");
        pickupLabel.setTextSize(16);
        pickupLabel.setTypeface(null, android.graphics.Typeface.BOLD);
        layout.addView(pickupLabel);

        layout.addView(pickupSpinner);

        TextView optionLabel = new TextView(this);
        optionLabel.setText("\nChoose Order Type");
        optionLabel.setTextSize(16);
        optionLabel.setTypeface(null, android.graphics.Typeface.BOLD);
        layout.addView(optionLabel);

        layout.addView(optionGroup);

        AlertDialog checkoutDialog =
                new AlertDialog.Builder(this)
                        .setTitle("DineDesk Checkout")
                        .setView(layout)
                        .setNegativeButton("Continue Shopping", null)
                        .setPositiveButton("Place Order", null)
                        .create();

        checkoutDialog.setOnShowListener(dialog -> {
            checkoutDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(v -> {

                        if (optionGroup.getCheckedRadioButtonId()
                                == -1) {
                            Toast.makeText(this,
                                    "Please select an order type.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        pickupSlot = pickupSpinner
                                .getSelectedItem().toString();

                        int selectedId =
                                optionGroup.getCheckedRadioButtonId();

                        android.widget.RadioButton selectedOption =
                                optionGroup.findViewById(selectedId);

                        orderOption = selectedOption.getText().toString();

                        checkoutDialog.dismiss();
                        placeOrder();
                    });
        });

        checkoutDialog.show();
    }


    private void placeOrder() {

        // Generate a demo order token.
        String orderId = "DD"
                + (System.currentTimeMillis() % 1000000);

        // Prepare each ordered item with quantity and line total.
        ArrayList<String> receiptItems = new ArrayList<>();

        for (Map.Entry<String, Integer> item
                : cartItems.entrySet()) {

            String foodName = item.getKey();
            int quantity = item.getValue();

            double price = foodPrices.containsKey(foodName)
                    ? foodPrices.get(foodName)
                    : 0.0;

            double lineTotal = price * quantity;

            String line = foodName
                    + " x " + quantity
                    + " — "
                    + String.format(
                    Locale.getDefault(),
                    "₹%.2f",
                    lineTotal);

            receiptItems.add(line);
        }
            OrderStore.orders.add(
                    new OrderStore.OrderRecord(
                            orderId,
                            username,
                            pickupSlot,
                            orderOption,
                            "Pending",
                            receiptItems,
                            cartTotal
                    )
            );

        // Pass the order details to the receipt screen.
        Intent intent = new Intent(
                StudentDashboardActivity.this,
                OrderReceiptActivity.class);

        intent.putExtra("token", orderId);
        intent.putExtra("customer", username);

        // Default pickup details until selectors are added.
        intent.putExtra("pickup", pickupSlot);
        intent.putExtra("option", orderOption);
        intent.putExtra("status", "Pending");

        intent.putStringArrayListExtra(
                "items", receiptItems);

        // Pass subtotal before GST.
        intent.putExtra("subtotal", cartTotal);

        // Clear the cart after preparing the order.
        cartItems.clear();
        cartCount = 0;
        cartTotal = 0.0;

        tvCartCount.setText("Cart: 0 items");
        tvCartTotal.setText("Total: ₹0.00");

        startActivity(intent);
    }
}
