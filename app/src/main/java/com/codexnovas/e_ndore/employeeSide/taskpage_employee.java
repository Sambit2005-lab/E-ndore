package com.codexnovas.e_ndore.employeeSide;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codexnovas.e_ndore.Complaint;
import com.codexnovas.e_ndore.ComplaintsAdapter;
import com.codexnovas.e_ndore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class taskpage_employee extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApplicationAdapter adapter;
    private List<Application> applications = new ArrayList<>();
    // Use a custom Application class
    private RecyclerView complaintsRecyclerView;
    private String employeeDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpage_employee); // Make sure to set the content view

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.current_task_recycler);
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView not found. Check the layout file.");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ApplicationAdapter(this, applications);
        recyclerView.setAdapter(adapter);

        complaintsRecyclerView = findViewById(R.id.current_task_recycler);

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("employees")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("department");

        employeeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employeeDepartment = snapshot.getValue(String.class);
                loadComplaints();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(taskpage_employee.this, "Error fetching department", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComplaints() {
        FirebaseFirestore.getInstance().collectionGroup("complaintsByCategory")
                .whereEqualTo("department", employeeDepartment)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Complaint> complaints = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        complaints.add(doc.toObject(Complaint.class));
                    }
                    ComplaintsAdapter adapter = new ComplaintsAdapter(complaints);
                    complaintsRecyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading complaints", Toast.LENGTH_SHORT).show();
                });


        // Fetch user ID from FirebaseAuth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            fetchLandDetailsForUser(userId);
        } else {
            // Handle case where user is not authenticated
            Log.e("taskpage_employee", "User is not authenticated.");
            // Maybe redirect to login activity or show an error
        }
    }

    private void fetchLandDetailsForUser(String userId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users")
                .document(userId)
                .collection("landDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            applications.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();

                                // Add Application objects to the list
                                applications.add(new Application(documentId));
                                Log.d("taskpage_employee", "Document added: " + documentId);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("taskpage_employee", "Error fetching documents: ", task.getException());
                        }
                    }
                });
    }
}
