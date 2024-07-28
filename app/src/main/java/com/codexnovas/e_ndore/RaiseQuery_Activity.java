package com.codexnovas.e_ndore;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import necessary packages
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RaiseQuery_Activity extends AppCompatActivity {
    private EditText queryEditText, areaEditText, daysEditText;
    private Spinner departmentSpinner;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_query);

        queryEditText = findViewById(R.id.age_edit);
        areaEditText = findViewById(R.id.area_edit);
        daysEditText = findViewById(R.id.phone_edit);
        departmentSpinner = findViewById(R.id.select_document);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        findViewById(R.id.next_btn).setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String userId = auth.getCurrentUser().getUid();
        String department = departmentSpinner.getSelectedItem().toString();
        String query = queryEditText.getText().toString();
        String area = areaEditText.getText().toString();
        String days = daysEditText.getText().toString();

        Map<String, Object> complaint = new HashMap<>();
        complaint.put("department", department);
        complaint.put("description", query);
        complaint.put("area", area);
        complaint.put("days", days);

        firestore.collection("complaints")
                .document(userId)
                .collection("complaintsByCategory")
                .add(complaint)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Complaint submitted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error submitting complaint", Toast.LENGTH_SHORT).show();
                });
    }
}