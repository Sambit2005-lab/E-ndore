package com.codexnovas.e_ndore;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomePage_Activity extends AppCompatActivity {

    // Initialise the variable
    private AppCompatButton HostBtn;
    private AppCompatButton EmployeeBtn;
    private AppCompatButton UserBtn;
    private AppCompatButton RegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        // Set the variable with their id
        HostBtn = findViewById(R.id.host_btn);
        EmployeeBtn = findViewById(R.id.employee_btn);
        UserBtn = findViewById(R.id.user_btn);
        RegisterBtn = findViewById(R.id.register_btn);



    }
}