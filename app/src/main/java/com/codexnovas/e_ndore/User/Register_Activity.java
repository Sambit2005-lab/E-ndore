package com.codexnovas.e_ndore.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.codexnovas.e_ndore.InfoPageUserSideActivity;
import com.codexnovas.e_ndore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {

    private EditText enterUsername, enterPassword, confirmPassword;
    private AppCompatImageButton nextBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        enterUsername = findViewById(R.id.enter_username);
        enterPassword = findViewById(R.id.enter_password);
        confirmPassword = findViewById(R.id.confirm_password_edit);
        nextBtn = findViewById(R.id.next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = enterUsername.getText().toString().trim();
                String password = enterPassword.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    enterUsername.setError("Email is required.");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    enterUsername.setError("Please enter a valid email address.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    enterPassword.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    enterPassword.setError("Password must be at least 6 characters.");
                    return;
                }

                if (!password.equals(confirmPass)) {
                    confirmPassword.setError("Passwords do not match.");
                    return;
                }

                registerUser(email, password);
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification()
                                    .addOnCompleteListener(verifyTask -> {
                                        if (verifyTask.isSuccessful()) {
                                            Toast.makeText(Register_Activity.this, "Registration successful. Please check your email for verification.", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Register_Activity.this, InfoPageUserSideActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(Register_Activity.this, "Failed to send verification email.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(Register_Activity.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}