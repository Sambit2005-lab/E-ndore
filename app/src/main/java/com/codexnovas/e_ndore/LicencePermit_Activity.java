package com.codexnovas.e_ndore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codexnovas.e_ndore.User.LandDetails_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LicencePermit_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_licence_permit);
        // Inside your CardView click listener
        CardView cardView = findViewById(R.id.Land_property_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected category from the CardView's tag
                String selectedCategory = (String) v.getTag();

                // Get the currently logged-in user's ID
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userId = auth.getCurrentUser().getUid();

                // Update Firestore
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                DocumentReference userRef = firestore.collection("users").document(userId);

                // Use a map to store the selected category
                Map<String, Object> updates = new HashMap<>();
                updates.put("applicationCategory", selectedCategory);

                userRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Category updated successfully
                            // Now navigate to the desired fragment
                            Intent intent = new Intent(LicencePermit_Activity.this, LandDetails_Activity.class);
                            startActivity(intent);
                        } else {
                            // Handle error
                            Toast.makeText(getApplicationContext(), "Error updating category", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}