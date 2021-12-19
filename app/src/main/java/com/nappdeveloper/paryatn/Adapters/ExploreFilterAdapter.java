package com.nappdeveloper.paryatn.Adapters;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nappdeveloper.paryatn.Fragments.ExploreCompanyFragment;
import com.nappdeveloper.paryatn.Fragments.filterFragment;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;

public class ExploreFilterAdapter extends FirebaseRecyclerAdapter<Model, ExploreFilterAdapter.Viewholder> {

    int selected_position = 0;

    public ExploreFilterAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ExploreFilterAdapter.Viewholder holder, int position, @NonNull Model model) {

        String name=model.getFilterName().toString();
        holder.filterNameTxt.setText(name);

        if(selected_position == position){
            holder.filterNameTxt.setTextColor(Color.parseColor("White"));
            TextViewCompat.setTextAppearance(holder.filterNameTxt, R.style.HUGEText);
            holder.linearLayout.setBackgroundResource(R.drawable.background_bookbtn);
        }else{
            holder.filterNameTxt.setTextColor(Color.parseColor("Black"));
            TextViewCompat.setTextAppearance(holder.filterNameTxt, R.style.smallText);
            holder.linearLayout.setBackgroundResource(R.drawable.background_search);
        }

        holder.filterNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ExploreCompanyFragment();
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager(); // this is basically context of the class
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("name",name); //key and value
                //set Fragmentclass Arguments
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.exploreFilterLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                int previousItem = selected_position;
                selected_position = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);

            }
        });
    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged();
        super.onDataChanged();
    }

    @NonNull
    @Override
    public ExploreFilterAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_filter_layout, parent, false);
        return new ExploreFilterAdapter.Viewholder(view);

    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {

        TextView filterNameTxt;
        LinearLayout linearLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.filter_element);
            filterNameTxt = (TextView) itemView.findViewById(R.id.filterNameTxt);

        }
    }


}
