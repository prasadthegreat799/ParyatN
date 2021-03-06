package com.nappdeveloper.paryatn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.ExploreAdapter;
import com.nappdeveloper.paryatn.Adapters.FilterAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class ExploreCompanyFragment extends Fragment {


    RecyclerView recyclerView;
    ExploreAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_explore_company, container, false);
        String name = getArguments().getString("name"); //fetching value by key
        databaseReference = FirebaseDatabase.getInstance().getReference().child("filterCompanies").child(name);
        recyclerView = (RecyclerView) view.findViewById(R.id.ExploreCompanyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.getRecycledViewPool().clear();


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new ExploreAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        adapter.stopListening();
    }

}