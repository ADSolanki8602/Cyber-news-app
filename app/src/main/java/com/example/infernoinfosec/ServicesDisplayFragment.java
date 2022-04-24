package com.example.infernoinfosec;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infernoinfosec.recycle_view.model;
import com.example.infernoinfosec.recycle_view.myadepter;
import com.example.infernoinfosec.recycle_view.service_adapter;
import com.example.infernoinfosec.recycle_view.service_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ServicesDisplayFragment extends Fragment {

    RecyclerView recview;
    ArrayList<service_model> datalist;
    service_adapter adapter;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_services_display, container, false);
        recview=view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        datalist=new ArrayList<>();

        adapter=new service_adapter(datalist,getContext());
        recview.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("cyDef").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                        try {
                            for(DocumentSnapshot d:list){
                                service_model obj=d.toObject(service_model.class);
                                datalist.add(obj);
                                //Adepter
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();            }
        });
        return view;
    }
}