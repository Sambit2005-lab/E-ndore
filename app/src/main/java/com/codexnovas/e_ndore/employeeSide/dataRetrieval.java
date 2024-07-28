package com.codexnovas.e_ndore.employeeSide;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.User.User;
import com.codexnovas.e_ndore.User.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class dataRetrieval extends AppCompatActivity {

    private FirebaseFirestore db;
    private Spinner fieldSpinner, valueSpinner;
    private RecyclerView recyclerView;
    private Button searchButton;
    private ArrayAdapter<String> valueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_data_retrieval);

        db = FirebaseFirestore.getInstance();
        fieldSpinner = findViewById(R.id.fieldSpinner);
        valueSpinner = findViewById(R.id.valueSpinner);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        setupFieldSpinner();
        setupSearchButton();
    }

    private void setupFieldSpinner() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("gender");
        fields.add("ageGroup");
        fields.add("nocPurpose");

        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fields);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldSpinner.setAdapter(fieldAdapter);

        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedField = fields.get(position);
                populateValueSpinner(selectedField);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void populateValueSpinner(String field) {
        List<String> values = new ArrayList<>();
        switch (field) {
            case "name":
                values.add("Rajesh Kumar");
                values.add("Priya Sharma");
                values.add("Amit Joshi");
                values.add("Rekha Gupta");
                values.add("Vikram Singh");
                break;
            case "gender":
                values.add("Male");
                values.add("Female");
                break;
            case "ageGroup":
                values.add("18-35");
                values.add("36-45");
                values.add("46-60");
                break;
            case "nocPurpose":
                values.add("Property Sale");
                values.add("Higher Studies Abroad");
                values.add("New Business Setup");
                values.add("Job Application");
                break;
        }

        valueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        valueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valueSpinner.setAdapter(valueAdapter);
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedField = fieldSpinner.getSelectedItem().toString();
                String selectedValue = valueSpinner.getSelectedItem().toString();
                performSearch(selectedField, selectedValue);
            }
        });
    }

    private void performSearch(String field, String value) {
        CollectionReference usersRef = db.collection("users");
        usersRef.whereEqualTo(field, value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> userList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                userList.add(user);
                            }
                            displayResults(userList);
                        } else {
                            Toast.makeText(dataRetrieval.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayResults(List<User> userList) {
        UserAdapter adapter = new UserAdapter(userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

