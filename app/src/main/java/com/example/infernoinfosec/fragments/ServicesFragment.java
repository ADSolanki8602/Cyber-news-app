package com.example.infernoinfosec.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infernoinfosec.CreatePostActivity;
import com.example.infernoinfosec.R;
import com.example.infernoinfosec.ServicesDisplayFragment;
import com.example.infernoinfosec.ServicesSubActivity;
import com.example.infernoinfosec.services.CyberDefanseFragment;
import com.example.infernoinfosec.services.CyberResponseFragment;
import com.example.infernoinfosec.services.CyberStratagyFragment;
import com.example.infernoinfosec.services.CyberTrainingFragment;
import com.example.infernoinfosec.services.CyberTransformationFragment;


public class ServicesFragment extends Fragment {
    CardView def,resp,tras,stra,train;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_services, container, false);


        def=view.findViewById(R.id.cyDefanseCard);
        resp=view.findViewById(R.id.cyResponseCard);
        tras=view.findViewById(R.id.cyTransfomationCard);
        stra=view.findViewById(R.id.cyStratagyCard);
        train=view.findViewById(R.id.cyTrainingCard);

        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServicesSubActivity.class);
                intent.putExtra("servKey",1);
                startActivity(intent);
            }
        });
        resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServicesSubActivity.class);
                intent.putExtra("servKey",2);
                startActivity(intent);
            }
        });
        tras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServicesSubActivity.class);
                intent.putExtra("servKey",3);
                startActivity(intent);
            }
        });
        stra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServicesSubActivity.class);
                intent.putExtra("servKey",4);
                startActivity(intent);
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServicesSubActivity.class);
                intent.putExtra("servKey",5);
                startActivity(intent);
            }
        });
        
        return view;
    }
}