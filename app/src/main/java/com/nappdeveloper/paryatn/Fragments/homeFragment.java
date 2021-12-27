package com.nappdeveloper.paryatn.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.Activities.searchBarActivity;
import com.nappdeveloper.paryatn.Activities.splashActivity;
import com.nappdeveloper.paryatn.Activities.studentPartnerActivity;
import com.nappdeveloper.paryatn.Activities.tourismPartnerActivity;
import com.nappdeveloper.paryatn.Adapters.FilterAdapter;
import com.nappdeveloper.paryatn.Adapters.PopularCategoriesAdapter;
import com.nappdeveloper.paryatn.Model.Model;
import com.nappdeveloper.paryatn.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class homeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView filterRecyclerView;
    FilterAdapter filterAdapter;
    DatabaseReference filterDatabaseReference;

    RecyclerView popularCategoriesRecyclerView;
    PopularCategoriesAdapter popularCategoriesAdapter;
    DatabaseReference popularCategoriesDatabaseReference;


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.microsoft, R.drawable.microsoft, R.drawable.microsoft};
    CircularImageView profileImg;
    ConstraintLayout sexyLogoLayout;
    LinearLayout userGreet;
    ImageView searchIcon;
    EditText searchTxt;

    CardView profileImageCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        sexyLogoLayout = (ConstraintLayout) view.findViewById(R.id.sexyLogoLayout);
        profileImageCard = (CardView) view.findViewById(R.id.profilePicCardView);
        userGreet = (LinearLayout) view.findViewById(R.id.userGreet);
        searchIcon = (ImageView) view.findViewById(R.id.searchIcon);
        searchTxt = (EditText) view.findViewById(R.id.searchTxt);

        profileImg = (CircularImageView) view.findViewById(R.id.HomeProfileImg);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_account_balance_24);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        navigationView = (NavigationView) view.findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.title_sideMenu);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_sidemenu = menu.findItem(R.id.title_sideMenu);
        navigationView.setNavigationItemSelectedListener(this);

        filterDatabaseReference = FirebaseDatabase.getInstance().getReference().child("filterNamesList");
        filterRecyclerView = (RecyclerView) view.findViewById(R.id.filterRecyclerView);
        filterRecyclerView.hasFixedSize();
        filterRecyclerView.setItemAnimator(null);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        filterRecyclerView.getRecycledViewPool().clear();


        popularCategoriesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("companyList");
        popularCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.popularCategoriesRecyclerView);
        popularCategoriesRecyclerView.hasFixedSize();
        popularCategoriesRecyclerView.setItemAnimator(null);
        popularCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));;
        popularCategoriesRecyclerView.getRecycledViewPool().clear();


        //To show the filter data in home frame layout
        Fragment fragment = new filterFragment();
        FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name", "All");
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.filterLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        searchTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Intent intent = new Intent(getContext(), searchBarActivity.class);
                startActivity(intent);
            }
        });


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(filterDatabaseReference, Model.class)
                        .build();
        filterAdapter = new FilterAdapter(options);
        filterRecyclerView.setAdapter(filterAdapter);




        FirebaseRecyclerOptions<Model> pcOptions =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(popularCategoriesDatabaseReference, Model.class)
                        .build();

        popularCategoriesAdapter = new PopularCategoriesAdapter(pcOptions);
        popularCategoriesRecyclerView.setAdapter(popularCategoriesAdapter);


        String userId = GoogleSignIn.getLastSignedInAccount(getContext()).getId().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (getActivity() == null) {
                            return;
                        }
                        String imageLink = snapshot.child("profilePic").getValue().toString();


                        Glide.with(getActivity()).load(imageLink).into(profileImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // load the animation
        Animation animPushIn = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.pushin_right);

        // start the animation
        sexyLogoLayout.startAnimation(animPushIn);

        // load the animation
        Animation animRotate = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.rotation_pushin);

        // start the animation
        profileImageCard.startAnimation(animRotate);

        // load the animation
        Animation animFadeIn = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.fadein);

        // start the animation
        userGreet.startAnimation(animPushIn);

        // load the animation
        Animation animPushLeftIn = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.pushin_leftin);

        // start the animation
        searchIcon.startAnimation(animPushLeftIn);

        // load the animation
        Animation animFadeIn2 = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.fadein);

        // start the animation
        searchTxt.startAnimation(animFadeIn2);


        return view;

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        filterAdapter.startListening();
        popularCategoriesAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        filterAdapter.stopListening();
        popularCategoriesAdapter.stopListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profileMenu:
                Toast.makeText(getContext(), "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.studentPartnerMenu:
                Intent studentIntent = new Intent(getContext(), studentPartnerActivity.class);
                startActivity(studentIntent);
                break;


            case R.id.tourismPartnerMenu:
                Intent tourismPartnerIntent = new Intent(getContext(), tourismPartnerActivity.class);
                startActivity(tourismPartnerIntent);
                break;

            case R.id.contactusSliderMenu:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "paryatn.info@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "your subject goes here...");
                    intent.putExtra(Intent.EXTRA_TEXT, "Complaint From ParyatN User App");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;

            case R.id.shareSliderMenu:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey Hi, " +
                        "Let Me Introduce you to this app called ParyatN.\n" +
                        "        This is an amazing app that teaches you practical knowledge through tours of industries.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ParyatN");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.logoutSliderMenu:
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
                break;
            default:
                Toast.makeText(getContext(), "Please,Select Any Option", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}