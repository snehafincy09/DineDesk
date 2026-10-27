
package com.example.dinedesk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        Button btnStudentLogin = findViewById(R.id.btnStudentLogin);
        Button btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnStudentLogin.setOnClickListener(v -> login("student"));
        btnAdminLogin.setOnClickListener(v -> login("admin"));
    }

    private void login(String role) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Please enter username and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        if (role.equals("student")
                && ((username.equals("Sneha") && password.equals("student1"))
                || (username.equals("Niranjana") && password.equals("student2"))
        )) {

            Intent intent = new Intent(
                    LoginActivity.this,
                    StudentDashboardActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);

        } else if (role.equals("admin")
                && username.equals("admin")
                && password.equals("admin123")) {

            Intent intent = new Intent(
                    LoginActivity.this,
                    AdminDashboardActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this,
                    "Invalid username, password, or login type",
                    Toast.LENGTH_SHORT).show();
        }

    }
}


