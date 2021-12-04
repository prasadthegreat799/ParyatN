package com.nappdeveloper.paryatn.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.FilterAdapter;
import com.nappdeveloper.paryatn.Adapters.PopularCategoriesAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class homeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView filterRecyclerView;
    FilterAdapter filterAdapter;
    DatabaseReference filterDatabaseReference;

    RecyclerView popularCategoriesRecyclerView;
    PopularCategoriesAdapter popularCategoriesAdapter;
    DatabaseReference popularCategoriesDatabaseReference;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) view.findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        filterDatabaseReference = FirebaseDatabase.getInstance().getReference().child("filterNamesList");
        filterRecyclerView = (RecyclerView) view.findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        filterRecyclerView.getRecycledViewPool().clear();



        popularCategoriesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("companyList");
        popularCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.popularCategoriesRecyclerView);
        popularCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularCategoriesRecyclerView.getRecycledViewPool().clear();



        Fragment fragment = new filterFragment();
        FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name","All");
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.filterLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profileMenu:
                Toast.makeText(getContext(),"Profile",Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getContext(), "Please,Select Any Option", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}