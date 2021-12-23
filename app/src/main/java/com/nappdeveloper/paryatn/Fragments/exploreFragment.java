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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.ExploreAdapter;
import com.nappdeveloper.paryatn.Adapters.ExploreFilterAdapter;
import com.nappdeveloper.paryatn.Adapters.FilterAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class exploreFragment extends Fragment {

    RecyclerView recyclerView;
    ExploreFilterAdapter adapter;
    DatabaseReference databaseReference;
    ImageView searchIcon;
    EditText searchTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_explore, container, false);

        searchIcon = (ImageView) view.findViewById(R.id.searchIconExplore);
        searchTxt = (EditText) view.findViewById(R.id.searchTxtExplore);

        databaseReference =FirebaseDatabase.getInstance().getReference().child("filterNamesList");
        recyclerView = (RecyclerView) view.findViewById(R.id.exploreFilterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.getRecycledViewPool().clear();

        Fragment fragment = new ExploreCompanyFragment();
        FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name","All");
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.exploreFilterLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new ExploreFilterAdapter(options);
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