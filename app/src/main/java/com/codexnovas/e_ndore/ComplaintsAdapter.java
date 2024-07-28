package com.codexnovas.e_ndore;
// ComplaintsAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder> {
    private List<Complaint> complaints;

    public ComplaintsAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_current_task_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        holder.titleTextView.setText(complaint.getTitle());
        holder.descriptionTextView.setText(complaint.getDescription());
        holder.departmentTextView.setText(complaint.getDepartment());
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView departmentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.complaint_id);
            descriptionTextView = itemView.findViewById(R.id.complaint_id_number);

        }
    }
}