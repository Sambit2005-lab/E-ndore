package com.codexnovas.e_ndore;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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


        openChatFragment("workspace_id");
    }

    private void openChatFragment(String workspaceId) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("WORKSPACE_ID", workspaceId);
        chatFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, chatFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}