package com.nappdeveloper.paryatn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.R;

public class profileFragment extends Fragment {

    CircularImageView profileImg;
    TextView userName,userMail,userCollege,userLocation,userBranch;
    Button signOutBtn,shareBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg=(CircularImageView) view.findViewById(R.id.ProfileImg);
        userName=(TextView) view.findViewById(R.id.UserNameTxt);
        userMail=(TextView) view.findViewById(R.id.UserMailTxt);
        userBranch=(TextView) view.findViewById(R.id.UserBranchTxt);
        userCollege=(TextView) view.findViewById(R.id.UserCollegeTxt);
        userLocation=(TextView) view.findViewById(R.id.UserLocationTxt);

        signOutBtn=(Button) view.findViewById(R.id.SignOutBtn);
        shareBtn=(Button) view.findViewById(R.id.ShareBtn);


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(view.getContext(),"Signout Btn",Toast.LENGTH_SHORT).show();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Share Btn",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}