package com.nappdeveloper.paryatn.Fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.Adapters.ExploreAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;


public class challengesFragment extends Fragment {


    LottieAnimationView animView;
    TextView projectsTxt,winnersTxt,leaderBoardTxt;

    NestedScrollView nestedScrollView;

    LinearLayout challengesNav, projectsLayout, winnersLayout, leaderboardLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        animView = (LottieAnimationView) view.findViewById(R.id.animation_celeb);
        projectsTxt=(TextView) view.findViewById(R.id.projectsTxt);
        winnersTxt=(TextView) view.findViewById(R.id.winnersTxt);
        leaderBoardTxt=(TextView) view.findViewById(R.id.leaderBoardTxt);

        challengesNav = (LinearLayout) view.findViewById(R.id.challengesNav);
        projectsLayout = (LinearLayout) view.findViewById(R.id.projectsLayout);
        winnersLayout = (LinearLayout) view.findViewById(R.id.winnersLayout);
        leaderboardLayout = (LinearLayout) view.findViewById(R.id.leaderBoardLayout);

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollChallenges);
        nestedScrollView.smoothScrollTo(0,challengesNav.getTop());


        float progress = animView.getProgress();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-progress,0 ).setDuration((long) ( animView.getDuration()* progress));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                animView.setProgress(Math.abs((float)animation.getAnimatedValue()));
            }
        });
        valueAnimator.start();


        //To show the filter data in home frame layout
        Fragment fragment = new projectsFragment();
        FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.projectFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        projectsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                projectsLayout.setBackgroundColor(getResources().getColor(R.color.purple_500));
                winnersLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));
                leaderboardLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));

                projectsTxt.setTextColor(getResources().getColor(R.color.white));
                winnersTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));
                leaderBoardTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));

                //To show the filter data in home frame layout
                Fragment fragment = new projectsFragment();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.projectFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        winnersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                winnersLayout.setBackgroundColor(getResources().getColor(R.color.purple_500));
                projectsLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));
                leaderboardLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));

                winnersTxt.setTextColor(getResources().getColor(R.color.white));
                projectsTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));
                leaderBoardTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));


                //To show the filter data in home frame layout
                Fragment fragment = new winnersFragment();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.projectFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        leaderboardLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                leaderboardLayout.setBackgroundColor(getResources().getColor(R.color.purple_500));
                winnersLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));
                projectsLayout.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));

                leaderBoardTxt.setTextColor(getResources().getColor(R.color.white));
                winnersTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));
                projectsTxt.setTextColor(getResources().getColor(R.color.browser_actions_text_color));

                //To show the filter data in home frame layout
                Fragment fragment = new leaderBoardFragment();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.projectFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }


}