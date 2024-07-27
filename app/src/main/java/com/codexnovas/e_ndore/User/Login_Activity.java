package com.codexnovas.e_ndore.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.codexnovas.e_ndore.LicencePermit_Activity;
import com.codexnovas.e_ndore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    private EditText enterUsername, enterPassword;
    private FirebaseAuth auth;
    private AppCompatButton signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enterUsername = findViewById(R.id.enter_username);
        enterPassword = findViewById(R.id.enter_password);
        signInBtn = findViewById(R.id.sign_in_btn);

        auth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = enterUsername.getText().toString().trim();
                String password = enterPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    enterUsername.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    enterPassword.setError("Password is required.");
                    return;
                }

                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            // Proceed to the main activity
                            startActivity(new Intent(Login_Activity.this, LicencePermit_Activity.class));
                            Toast.makeText(Login_Activity.this, "Login successful.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(Login_Activity.this, "Please verify your email address.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Login_Activity.this, "Login failed. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}