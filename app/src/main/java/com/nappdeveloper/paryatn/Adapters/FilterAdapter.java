package com.nappdeveloper.paryatn.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class FilterAdapter extends FirebaseRecyclerAdapter<Model, FilterAdapter.Viewholder> {


    public FilterAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull FilterAdapter.Viewholder holder, int position, @NonNull Model model) {

        holder.filterNameTxt.setText(model.getFilterName());


    }


    @NonNull
    @Override
    public FilterAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_filter_layout, parent, false);
        return new FilterAdapter.Viewholder(view);

    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {

        TextView filterNameTxt;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            filterNameTxt = (TextView) itemView.findViewById(R.id.filterNameTxt);

        }
    }


}
