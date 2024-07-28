package com.codexnovas.e_ndore.employeeSide;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codexnovas.e_ndore.R;
import com.codexnovas.e_ndore.workStatusdisplay;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class workspace_tools_bottomsheet extends BottomSheetDialogFragment {

    private AppCompatButton work_status_btn,Share_btn,Retrieve_Data_btn;

    public workspace_tools_bottomsheet() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_workspace_tools_bottomsheet, container, false);

        work_status_btn=view.findViewById(R.id.work_status_btn);
        Share_btn=view.findViewById(R.id.Share_btn);
        Retrieve_Data_btn=view.findViewById(R.id.Retrieve_Data_btn);

        work_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), workStatusdisplay.class);
                startActivity(intent);
            }
        });

        Share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), documentSharing.class);
                startActivity(intent);
            }
        });

        Retrieve_Data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), dataRetrieval.class);
                startActivity(intent);
            }
        });

        return view;
    }
}