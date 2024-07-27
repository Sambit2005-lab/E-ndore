package com.codexnovas.e_ndore.employeeSide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.workspace;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private List<Application> applications;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView applicationIdTextView;
        public AppCompatImageButton viewWorkspaceButton;

        public ViewHolder(View view) {
            super(view);
            applicationIdTextView = view.findViewById(R.id.complaint_id_number);
            viewWorkspaceButton = view.findViewById(R.id.view_workspace_btn);
        }
    }

    public ApplicationAdapter(Context context, List<Application> applications) {
        this.context = context;
        this.applications = applications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_current_task_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application application = applications.get(position);
        holder.applicationIdTextView.setText("Application ID: " + application.getId());

        holder.viewWorkspaceButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, workspace.class);
            intent.putExtra("DOCUMENT_ID", application.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }
}