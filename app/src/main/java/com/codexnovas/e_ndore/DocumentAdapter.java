package com.codexnovas.e_ndore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {
    private List<Document> documents;
    private Context context;

    public DocumentAdapter(List<Document> documents, Context context) {
        this.documents = documents;
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        Document document = documents.get(position);
        holder.title.setText(document.getTitle());
        holder.icon.setOnClickListener(v -> {
            // Handle document click, maybe open the URL in a browser or a PDF viewer
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(document.getUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.document_title);
            icon = itemView.findViewById(R.id.document_icon);
        }
    }
}