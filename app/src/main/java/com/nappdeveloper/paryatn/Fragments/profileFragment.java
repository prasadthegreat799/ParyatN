package com.nappdeveloper.paryatn.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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
    TextView userName, userMail, userCollege, userLocation, userBranch, userPhone, signOutBtn, shareBtn, editBtn, tripsHistoryBtn, favToursBtn, settingsBtn, helpBtn;
    Toolbar toolbar;
    TextView userTrips,userRating;

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
        userPhone = (TextView) view.findViewById(R.id.UserPhoneTxt);
        editBtn = (TextView) view.findViewById(R.id.editBtn);
        tripsHistoryBtn = (TextView) view.findViewById(R.id.tripsHistory);
        favToursBtn = (TextView) view.findViewById(R.id.favTours);
        helpBtn = (TextView) view.findViewById(R.id.helpSupport);

        userTrips=(TextView) view.findViewById(R.id.userTripsTxt);
        userRating=(TextView) view.findViewById(R.id.userRatingTxt);


        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        signOutBtn = (TextView) view.findViewById(R.id.SignOutBtn);
        shareBtn = (TextView) view.findViewById(R.id.ShareBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view2 = factory.inflate(R.layout.edit_details_popup, null);
                EditText phoneNumberEditTxt = view2.findViewById(R.id.edit_number);
                EditText nameEditText = view2.findViewById(R.id.edit_name);
                alertadd.setView(view2);
                //Implementing OnClickListener to Open Website
                alertadd.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertadd.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final AlertDialog dialog = alertadd.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });


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

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey Hi," +
                        "Let Me Introduce you to this app called ParyatN.\n" +
                        "        This is an amazing app that teaches you practical knowledge through tours of industries.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ParyatN");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
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

                        String college = snapshot.child("userCollege").getValue().toString();
                        String branch = snapshot.child("userBranch").getValue().toString();
                        String phone = snapshot.child("userPhone").getValue().toString();
                        String rating = snapshot.child("userRating").getValue().toString();
                        String trips = snapshot.child("userTrips").getValue().toString();


                        Glide.with(getActivity()).load(imageLink).into(profileImg);
                        userName.setText(name);
                        userMail.setText(mail);

                        userCollege.setText(college);
                        userPhone.setText(phone);
                        userBranch.setText(branch);

                        userTrips.setText(trips);
                        userRating.setText(rating);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        /*// load the animation
        Animation animleftin = AnimationUtils.loadAnimation(getContext().getApplicationContext(),R.anim.pushin_left_profile_anim);

        // start the animation
        tripsHistoryBtn.startAnimation(animleftin);
        settingsBtn.startAnimation(animleftin);
        shareBtn.startAnimation(animleftin);

        // load the animation
        Animation animrightin = AnimationUtils.loadAnimation(getContext().getApplicationContext(),R.anim.pushin_right_profile_anim);

        // start the animation
        favToursBtn.startAnimation(animrightin);
        helpBtn.startAnimation(animrightin);
        signOutBtn.startAnimation(animrightin);*/

        return view;
    }
}