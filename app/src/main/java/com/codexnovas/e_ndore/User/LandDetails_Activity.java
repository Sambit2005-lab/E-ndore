package com.codexnovas.e_ndore.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.employeeSide.taskpage_employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LandDetails_Activity extends AppCompatActivity {

    private EditText propertyApplicationEdit, propertyIdEdit, propertyAreaEdit,
            propertyTaxEdit, swmEdit, waterEdit, electricEdit, licenceEdit;
    private AppCompatButton submitBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        propertyApplicationEdit = findViewById(R.id.property_application_edit);
        propertyIdEdit = findViewById(R.id.property_id_edit);
        propertyAreaEdit = findViewById(R.id.property_area_edit);
        propertyTaxEdit = findViewById(R.id.property_tax_edit);
        swmEdit = findViewById(R.id.swm_edit);
        waterEdit = findViewById(R.id.water_edit);
        electricEdit = findViewById(R.id.electric_edit);
        licenceEdit = findViewById(R.id.licence_edit);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLandDetails();
            }
        });
    }

    private void submitLandDetails() {
        String propertyApplication = propertyApplicationEdit.getText().toString().trim();
        String propertyId = propertyIdEdit.getText().toString().trim();
        String propertyArea = propertyAreaEdit.getText().toString().trim();
        String propertyTax = propertyTaxEdit.getText().toString().trim();
        String swm = swmEdit.getText().toString().trim();
        String water = waterEdit.getText().toString().trim();
        String electric = electricEdit.getText().toString().trim();
        String licence = licenceEdit.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(propertyApplication) || TextUtils.isEmpty(propertyId) ||
                TextUtils.isEmpty(propertyArea) || TextUtils.isEmpty(propertyTax) ||
                TextUtils.isEmpty(swm) || TextUtils.isEmpty(water) ||
                TextUtils.isEmpty(electric) || TextUtils.isEmpty(licence)) {
            Toast.makeText(LandDetails_Activity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current user ID
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Create a data map
        Map<String, Object> landDetails = new HashMap<>();
        landDetails.put("property_application", propertyApplication);
        landDetails.put("property_id", propertyId);
        landDetails.put("property_area", propertyArea);
        landDetails.put("property_tax", propertyTax);
        landDetails.put("swm", swm);
        landDetails.put("waterCharges", water);
        landDetails.put("electricCharges", electric);
        landDetails.put("licence", licence);

        // Save to Firestore
        firestore.collection("users").document(userId).collection("landDetails")
                .add(landDetails)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LandDetails_Activity.this, "Details submitted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LandDetails_Activity.this, taskpage_employee.class);
                            startActivity(intent);

                            // Optionally clear the form fields or navigate to another activity
                        } else {
                            Toast.makeText(LandDetails_Activity.this, "Error submitting details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}