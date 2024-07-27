package com.codexnovas.e_ndore.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codexnovas.e_ndore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class formCategoryDialogFragment extends DialogFragment {

    private RadioGroup radioGroup;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_form_category_dialog, null);

        radioGroup = view.findViewById(R.id.task_list_btn);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        builder.setView(view)
                .setTitle("Choose the category of application:")
                .setPositiveButton("OK", (dialog, id) -> {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = view.findViewById(selectedId);

                    if (selectedRadioButton != null) {
                        String category = selectedRadioButton.getText().toString();

                        if (currentUser != null) {
                            String userId = currentUser.getUid();

                            // Save category to Firestore
                            Map<String, Object> categoryMap = new HashMap<>();
                            categoryMap.put("applicationCategory", category);

                            db.collection("users").document(userId)
                                    .set(categoryMap, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                        if (getActivity() != null) {
                                            Toast.makeText(getActivity(), "Category saved successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("formCategoryDialog", "Activity is null, cannot show toast.");
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        if (getActivity() != null) {
                                            Toast.makeText(getActivity(), "Error saving category", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("formCategoryDialog", "Activity is null, cannot show toast.");
                                        }
                                    });
                        } else {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("formCategoryDialog", "Activity is null, cannot show toast.");
                            }
                        }
                    } else {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "No category selected", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("formCategoryDialog", "Activity is null, cannot show toast.");
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dismiss());

        return builder.create();
    }
}