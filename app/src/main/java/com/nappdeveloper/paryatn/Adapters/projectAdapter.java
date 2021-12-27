package com.nappdeveloper.paryatn.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class projectAdapter extends FirebaseRecyclerAdapter<Model, projectAdapter.Viewholder> {


    public projectAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull projectAdapter.Viewholder holder, int position, @NonNull Model model) {




    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged();
        super.onDataChanged();
    }

    @NonNull
    @Override
    public projectAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_list, parent, false);
        return new projectAdapter.Viewholder(view);

    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {



        public Viewholder(@NonNull View itemView) {
            super(itemView);



        }
    }
}

