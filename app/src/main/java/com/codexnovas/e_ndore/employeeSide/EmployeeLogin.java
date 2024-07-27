package com.codexnovas.e_ndore.employeeSide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.User.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLogin extends AppCompatActivity {

    private EditText employeeIdEditText, passwordEditText;
    private DatabaseReference databaseReference;
    private TextView resister;
    private TextView resister2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_login);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("employees");

        // Initialize views
        employeeIdEditText = findViewById(R.id.enter_username);
        passwordEditText = findViewById(R.id.enter_password);
        resister=findViewById(R.id.resister);
        resister2=findViewById(R.id.resister2);

        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmployee();
            }
        });

        findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password logic here
            }
        });

        resister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmployeeLogin.this, EmployeeRegister.class);
                startActivity(intent);
            }
        });

        resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmployeeLogin.this,EmployeeRegister.class);
                startActivity(intent);
            }
        });

    }

    private void loginEmployee() {
        String employeeId = employeeIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(employeeId)) {
            employeeIdEditText.setError("Employee ID is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        // Verify login credentials
        databaseReference.child(employeeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    if (employee != null && employee.password.equals(password)) {
                        Toast.makeText(EmployeeLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Start the main activity or dashboard
                        startActivity(new Intent(EmployeeLogin.this, taskpage_employee.class));
                    } else {
                        Toast.makeText(EmployeeLogin.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmployeeLogin.this, "Employee ID not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeLogin.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}