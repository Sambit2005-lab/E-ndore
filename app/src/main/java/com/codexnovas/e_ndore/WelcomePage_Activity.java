package com.codexnovas.e_ndore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        // register btn set up
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomePage_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });


        // Host Btn set up
        HostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WelcomePage_Activity.this,Login_Activity.class);
                startActivity(intent);
            }
        });


    }
}