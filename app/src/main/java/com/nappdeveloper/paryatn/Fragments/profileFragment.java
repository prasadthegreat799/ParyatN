package com.nappdeveloper.paryatn.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.Activities.splashActivity;
import com.nappdeveloper.paryatn.R;

public class profileFragment extends Fragment {

    CircularImageView profileImg;
    TextView userName, userMail, userCollege, userLocation, userBranch;
    Button signOutBtn, shareBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = (CircularImageView) view.findViewById(R.id.ProfileImg);
        userName = (TextView) view.findViewById(R.id.UserNameTxt);
        userMail = (TextView) view.findViewById(R.id.UserMailTxt);
        userBranch = (TextView) view.findViewById(R.id.UserBranchTxt);
        userCollege = (TextView) view.findViewById(R.id.UserCollegeTxt);
        userLocation = (TextView) view.findViewById(R.id.UserLocationTxt);

        signOutBtn = (Button) view.findViewById(R.id.SignOutBtn);
        shareBtn = (Button) view.findViewById(R.id.ShareBtn);


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            //User Signout
                            FirebaseAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().getTenantId();

                            //Redirecting to LoginActivity
                            Intent intent = new Intent(getContext(), splashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }
                });

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(view.getContext(), "Share Btn", Toast.LENGTH_SHORT).show();
            }
        });


        String userId = GoogleSignIn.getLastSignedInAccount(getContext()).getId().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (getActivity() == null) {
                            return;
                        }
                        String name = snapshot.child("userName").getValue().toString();
                        String mail = snapshot.child("mail").getValue().toString();
                        String imageLink = snapshot.child("profilePic").getValue().toString();


                        Glide.with(getActivity()).load(imageLink).into(profileImg);
                        userName.setText(name);
                        userMail.setText(mail);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }
}