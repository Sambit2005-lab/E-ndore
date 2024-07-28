package com.codexnovas.e_ndore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.List;

public class documentSharing extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private Button uploadFileButton;
    private RecyclerView documentRecyclerView;
    private DocumentAdapter documentAdapter;
    private List<Document> documentList;
    private DatabaseReference databaseReference;
    private String currentUserDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_document_sharing);

        uploadFileButton = findViewById(R.id.upload_file_button);
        documentRecyclerView = findViewById(R.id.document_recycler_view);
        documentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        documentList = new ArrayList<>();
        documentAdapter = new DocumentAdapter(documentList, this);
        documentRecyclerView.setAdapter(documentAdapter);

        uploadFileButton.setOnClickListener(v -> showFileChooser());

        getCurrentUserDepartment();
        loadDocuments();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            uploadFile(fileUri);
        }
    }

    private void uploadFile(Uri fileUri) {
        if (fileUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            String fileId = databaseReference.child("uploads").push().getKey(); // Generate unique key

            if (fileId != null) {
                StorageReference fileReference = storageReference.child("uploads/" + fileId);

                fileReference.putFile(fileUri)
                        .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            databaseReference.child("employees").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String department = dataSnapshot.child("department").getValue(String.class);
                                    String documentId = databaseReference.child("documents").push().getKey();
                                    Document document = new Document("Document Title", downloadUrl, department);
                                    databaseReference.child("documents").child(documentId).setValue(document);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle error
                                }
                            });
                        }))
                        .addOnFailureListener(e -> {
                            // Handle upload failure
                        });
            }
        }
    }

    private void getCurrentUserDepartment() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("employees").child(userId).child("department").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserDepartment = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadDocuments() {
        databaseReference.child("documents").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                documentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Document document = snapshot.getValue(Document.class);
                    if (document != null) {
                        documentList.add(document);
                    }
                }
                documentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}