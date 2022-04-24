package com.example.infernoinfosec.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.infernoinfosec.CreatePostActivity;
import com.example.infernoinfosec.R;
import com.example.infernoinfosec.recycle_view.model;
import com.example.infernoinfosec.recycle_view.myadepter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recview;
    ArrayList<model> datalist;
    myadepter adepter;
    FirebaseAuth fAuth;
    FloatingActionButton add;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore db=FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recview=view.findViewById(R.id.recview);
        add=view.findViewById(R.id.addPostBtn);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        datalist=new ArrayList<>();

        adepter=new myadepter(datalist,getContext());
        recview.setAdapter(adepter);

        fAuth=(FirebaseAuth) FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            add.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CreatePostActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        db.collection("News")
                .orderBy("key", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentChange> list=queryDocumentSnapshots.getDocumentChanges();

                            for(DocumentChange d:list){
                                if(d.getType()==DocumentChange.Type.ADDED){
                                    adepter.notifyDataSetChanged();
                                    model obj=d.getDocument().toObject(model.class);
                                    datalist.add(obj);
                                   // getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
                                    //Adepter
                                    adepter.notifyDataSetChanged();
                                }else if(d.getType()==DocumentChange.Type.REMOVED) {
                                    Toast.makeText(getContext(), "Item removed", Toast.LENGTH_SHORT).show();
                                   // getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
                                    adepter.notifyDataSetChanged();
                                }else {

                                   // getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
                                    adepter.notifyDataSetChanged();
                                }
                            }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();            }
        });

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adepter.notifyDataSetChanged();
                getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
               // restartActivity();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //Restart activity

        return view;
    }
    public void restartActivity(){
        Intent mIntent = getActivity().getIntent();
        getActivity().finish();
        startActivity(mIntent);
    }


}