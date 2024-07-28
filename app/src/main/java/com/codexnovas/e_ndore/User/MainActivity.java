package com.codexnovas.e_ndore.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codexnovas.e_ndore.LicencePermit_Activity;
import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.RaiseQuery_Activity;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DocumentReference userDocument;
    private TextView Name;
    private CardView cardView;
    private CardView applicationcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        cardView = findViewById(R.id.myCalendar_card);
        applicationcard=findViewById(R.id.application_card);

        applicationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LicencePermit_Activity.class);
                startActivity(intent);
            }
        });

        // slider
        // Initialize ImageSlider
        ImageSlider imageSlider =findViewById(R.id.imageSlider);

        // Creating a list of SlideModel
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.imc1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.imc2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.imc3, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.imc4, ScaleTypes.CENTER_CROP));

        // Add images to the ImageSlider
        imageSlider.setImageList(slideModels);
        imageSlider.startSliding(1500);



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RaiseQuery_Activity.class);
                startActivity(intent);
            }
        });

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
