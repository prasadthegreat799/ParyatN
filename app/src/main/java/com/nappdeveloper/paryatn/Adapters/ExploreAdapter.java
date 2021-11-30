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

public class ExploreAdapter extends FirebaseRecyclerAdapter<Model, ExploreAdapter.Viewholder> {


    public ExploreAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Model model) {

        Glide.with(holder.companyImage.getContext()).load(model.getCompanyLogo()).into(holder.companyImage);
        holder.companyName.setText(model.getCompanyName());
        holder.companyType.setText(model.getCompanyType());
        holder.companyRating.setText(model.getCompanyRating());
        holder.companyLocation.setText(model.getCompanyLocation());

    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged();
        super.onDataChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_company_layout, parent, false);
        return new Viewholder(view);

    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {

        CircularImageView companyImage;
        TextView companyName, companyType, companyRating, companyLocation;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            companyImage = (CircularImageView) itemView.findViewById(R.id.CompanyLogoImg);
            companyName = (TextView) itemView.findViewById(R.id.ComapanyNameTxt);
            companyType = (TextView) itemView.findViewById(R.id.CompanyTypeTxt);
            companyRating = (TextView) itemView.findViewById(R.id.CompanyRatingTxt);
            companyLocation = (TextView) itemView.findViewById(R.id.CompanyLocationTxt);

        }
    }


}

