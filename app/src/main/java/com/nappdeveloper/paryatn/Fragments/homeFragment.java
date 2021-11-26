package com.nappdeveloper.paryatn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.FilterAdapter;
import com.nappdeveloper.paryatn.Adapters.PopularCategoriesAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class homeFragment extends Fragment {

    RecyclerView filterRecyclerView;
    FilterAdapter filterAdapter;
    DatabaseReference filterDatabaseReference;

    RecyclerView popularCategoriesRecyclerView;
    PopularCategoriesAdapter popularCategoriesAdapter;
    DatabaseReference popularCategoriesDatabaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        filterDatabaseReference = FirebaseDatabase.getInstance().getReference().child("filterNamesList");
        filterRecyclerView = (RecyclerView) view.findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        popularCategoriesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("companyList");
        popularCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.popularCategoriesRecyclerView);
        popularCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(filterDatabaseReference, Model.class)
                        .build();
        filterAdapter = new FilterAdapter(options);
        filterRecyclerView.setAdapter(filterAdapter);


        FirebaseRecyclerOptions<Model> pcOptions =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(popularCategoriesDatabaseReference, Model.class)
                        .build();

        popularCategoriesAdapter= new PopularCategoriesAdapter(pcOptions);
        popularCategoriesRecyclerView.setAdapter(popularCategoriesAdapter);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        filterAdapter.startListening();
        popularCategoriesAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        filterAdapter.stopListening();
        popularCategoriesAdapter.stopListening();
    }
}