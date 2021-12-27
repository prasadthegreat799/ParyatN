package com.nappdeveloper.paryatn.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.ExploreFilterAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class searchBarActivity extends AppCompatActivity {

    EditText searchBox;

    RecyclerView recyclerView;
    ExploreFilterAdapter adapter;
    DatabaseReference databaseReference,searchRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        searchBox=(EditText) findViewById(R.id.searchBox);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("filterNamesList");
        searchRef=FirebaseDatabase.getInstance().getReference().child("filterCompanies").child("All");
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.getRecycledViewPool().clear();



        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(databaseReference, Model.class)
                        .build();

        adapter = new ExploreFilterAdapter(options);
        recyclerView.setAdapter(adapter);
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