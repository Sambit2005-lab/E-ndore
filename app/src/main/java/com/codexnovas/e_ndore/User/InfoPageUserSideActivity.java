package com.codexnovas.e_ndore.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codexnovas.e_ndore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class InfoPageUserSideActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText, genderEditText, addressEditText, phoneEditText;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user_side_page);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        nameEditText = findViewById(R.id.name_edit);
        ageEditText = findViewById(R.id.age_edit);
        genderEditText = findViewById(R.id.gender_edit);
        addressEditText = findViewById(R.id.address_edit);
        phoneEditText = findViewById(R.id.phone_edit);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });
    }

    private void saveUserInfo() {
        String name = nameEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference userRef = db.collection("users").document(userId);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("name", name);
            userInfo.put("age", age);
            userInfo.put("gender", gender);
            userInfo.put("address", address);
            userInfo.put("phone", phone);

            userRef.set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Data saved successfully
                        Toast.makeText(InfoPageUserSideActivity.this, "User information saved", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(InfoPageUserSideActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Handle error
                    }
                }
            });
        }
    }
}