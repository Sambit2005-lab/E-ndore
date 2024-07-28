package com.codexnovas.e_ndore.employeeSide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codexnovas.e_ndore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText textInput;
    private ImageButton sendButton;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore db;
    private String workspaceId;
    private AppCompatButton bottomSheetBtn;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_area);
        textInput = view.findViewById(R.id.text_input);
        sendButton = view.findViewById(R.id.send);
        bottomSheetBtn = view.findViewById(R.id.bottom_sheet_btn);

        bottomSheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workspace_tools_bottomsheet bottomsheetfragment = new workspace_tools_bottomsheet();
                bottomsheetfragment.show(getParentFragmentManager(), bottomsheetfragment.getTag());
            }
        });




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerView.setAdapter(chatAdapter);



        db = FirebaseFirestore.getInstance();

        if (getArguments() != null) {
            workspaceId = getArguments().getString("WORKSPACE_ID");
            fetchChatMessages();
        }

        sendButton.setOnClickListener(v -> {
            String messageText = textInput.getText().toString().trim();
            if (!TextUtils.isEmpty(messageText)) {
                sendMessage(messageText);
                textInput.setText("");
            }
        });

        return view;
    }

    private void fetchChatMessages() {
        if (workspaceId == null) return;

        db.collection("workspaces").document(workspaceId).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        // Handle error
                        return;
                    }
                    if (snapshots != null) {
                        List<ChatMessage> messages = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshots) {
                            ChatMessage message = doc.toObject(ChatMessage.class);
                            messages.add(message);
                        }
                        chatAdapter.setMessages(messages);
                    }
                });
    }

    private void sendMessage(String messageText) {
        if (workspaceId == null) return;

        Map<String, Object> message = new HashMap<>();
        message.put("sender_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        message.put("message", messageText);
        message.put("timestamp", com.google.firebase.Timestamp.now());

        db.collection("workspaces").document(workspaceId).collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    // Message sent
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
}
