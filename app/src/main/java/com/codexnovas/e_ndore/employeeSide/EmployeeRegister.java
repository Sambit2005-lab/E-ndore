package com.codexnovas.e_ndore.employeeSide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.workspace;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeRegister extends AppCompatActivity {

    private EditText employeeIdEditText, passwordEditText, confirmPasswordEditText;
    private Spinner departmentSpinner;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_register);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("employees");

        // Initialize views
        employeeIdEditText = findViewById(R.id.enter_EmployeeId);
        passwordEditText = findViewById(R.id.enter_password);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit);
        departmentSpinner = findViewById(R.id.lang_spinner);

        // Set up the Spinner with items
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(adapter);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmployee();
            }
        });
    }

    private void registerEmployee() {
        String employeeId = employeeIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String department = departmentSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(employeeId)) {
            employeeIdEditText.setError("Employee ID is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Store data in Firebase
        Employee employee = new Employee(employeeId, password, department);
        databaseReference.child(employeeId).setValue(employee);

        Toast.makeText(EmployeeRegister.this, "Employee Registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EmployeeRegister.this, workspace.class);
        startActivity(intent);
    }
}

class Employee {
    public String employeeId;
    public String password;
    public String department;

    public Employee() {
        // Default constructor required for calls to DataSnapshot.getValue(Employee.class)
    }

    public Employee(String employeeId, String password, String department) {
        this.employeeId = employeeId;
        this.password = password;
        this.department = department;
    }
}