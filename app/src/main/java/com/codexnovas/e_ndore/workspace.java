package com.codexnovas.e_ndore;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class workspace extends AppCompatActivity {

    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        button = findViewById(R.id.bottom_sheet_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workspace_tools_bottomsheet bottomsheetfragment =new workspace_tools_bottomsheet();
                bottomsheetfragment.show(getSupportFragmentManager(),bottomsheetfragment.getTag());

            }
        });


    }
}