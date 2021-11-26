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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class FilterLayoutAdapter extends FirebaseRecyclerAdapter<Model, FilterLayoutAdapter.Viewholder> {


    public FilterLayoutAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull FilterLayoutAdapter.Viewholder holder, int position, @NonNull Model model) {

        Glide.with(holder.companyImage.getContext()).load(model.getCompanyLogo()).into(holder.companyImage);
        holder.companyName.setText(model.getCompanyName());

    }


    @NonNull
    @Override
    public FilterLayoutAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_filtercompany_layout, parent, false);
        return new FilterLayoutAdapter.Viewholder(view);

    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {

        ImageView companyImage;
        TextView companyName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            companyImage = (ImageView) itemView.findViewById(R.id.companyImg);
            companyName = (TextView) itemView.findViewById(R.id.companyNameTxt);


        }
    }


}
