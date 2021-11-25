package com.nappdeveloper.paryatn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.PopularCategoriesAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class filterFragment extends Fragment {

    TextView textView;

    RecyclerView popularCategoriesRecyclerView;
    PopularCategoriesAdapter popularCategoriesAdapter;
    DatabaseReference popularCategoriesDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String name = getArguments().getString("name"); //fetching value by key
        View view = inflater.inflate(R.layout.fragment_filter, container, false);


        popularCategoriesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("filterCompanies").child(name);
        popularCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.filterLayoutRecyclerView);
        popularCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Model> pcOptions =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(popularCategoriesDatabaseReference, Model.class)
                        .build();

        popularCategoriesAdapter = new PopularCategoriesAdapter(pcOptions);
        popularCategoriesRecyclerView.setAdapter(popularCategoriesAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        popularCategoriesAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        popularCategoriesAdapter.stopListening();
    }
}