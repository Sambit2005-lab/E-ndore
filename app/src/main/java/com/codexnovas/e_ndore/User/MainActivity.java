package com.codexnovas.e_ndore.User;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codexnovas.e_ndore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DocumentReference userDocument;
    private TextView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            // Initialize Firestore DocumentReference
            userDocument = firestore.collection("users").document(userId);

            getUserNameFromFirestore();
        }
    }

    private void getUserNameFromFirestore() {
        userDocument.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.getString("name");
                    Name.setText(name);


                    // Do something with the user's name
                    Toast.makeText(MainActivity.this, "User's name: " + name, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
