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
import com.nappdeveloper.paryatn.Adapters.FilterLayoutAdapter;
import com.nappdeveloper.paryatn.Adapters.PopularCategoriesAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class filterFragment extends Fragment {

    RecyclerView filterLayoutRecyclerView;
    FilterLayoutAdapter filterLayoutAdapter;
    DatabaseReference filterLayoutDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String name = getArguments().getString("name"); //fetching value by key
        View view = inflater.inflate(R.layout.fragment_filter, container, false);


        filterLayoutDatabaseReference = FirebaseDatabase.getInstance().getReference().child("filterCompanies").child(name);
        filterLayoutRecyclerView = (RecyclerView) view.findViewById(R.id.filterLayoutRecyclerView);
        filterLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Model> pcOptions =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(filterLayoutDatabaseReference, Model.class)
                        .build();

        filterLayoutAdapter = new FilterLayoutAdapter(pcOptions);
        filterLayoutRecyclerView.setAdapter(filterLayoutAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        filterLayoutAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
       // filterLayoutAdapter.stopListening();
    }
}