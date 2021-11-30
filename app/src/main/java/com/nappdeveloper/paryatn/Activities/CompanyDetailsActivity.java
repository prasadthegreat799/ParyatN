package com.nappdeveloper.paryatn.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.MainActivity;
import com.nappdeveloper.paryatn.R;

import org.w3c.dom.Text;

public class CompanyDetailsActivity extends AppCompatActivity {

    ImageView companyImage;
    TextView companyNameTxt;
    DatabaseReference databaseReference;

    TextView companyOverviewTxt, companyDetailsTxt, companyReviewsTxt, companyTripCostTxt, companyTripDurationTxt, companyTripDistanceTxt;
    TextView companyDataTxt;

    View v1, v2, v3, v4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);


        String imageId = getIntent().getStringExtra("imageId");
        String imageCategory = getIntent().getStringExtra("imageCategory");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("filterCompanies").child(imageCategory).child(imageId);

        v1 = (View) findViewById(R.id.ul1);
        v2 = (View) findViewById(R.id.ul2);
        v3 = (View) findViewById(R.id.ul3);
        v4 = (View) findViewById(R.id.ul4);

        companyImage = (ImageView) findViewById(R.id.CompanyLogoImg);
        companyNameTxt = (TextView) findViewById(R.id.companyNameTxt);
        companyOverviewTxt = (TextView) findViewById(R.id.companyOverviewTxt);
        companyDetailsTxt = (TextView) findViewById(R.id.companyDetailsTxt);
        companyReviewsTxt = (TextView) findViewById(R.id.companyReviewTxt);
        companyTripCostTxt = (TextView) findViewById(R.id.companyTripCostTxt);
        companyTripDurationTxt = (TextView) findViewById(R.id.companyTripDurationTxt);
        companyTripDistanceTxt = (TextView) findViewById(R.id.companyTripDistanceTxt);

        companyDataTxt=(TextView) findViewById(R.id.companyDataTxt);


        companyOverviewTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);

                companyOverviewTxt.setTextColor(getResources().getColor(R.color.purple_500));
                companyDetailsTxt.setTextColor(Color.parseColor("#808080"));
                companyReviewsTxt.setTextColor(Color.parseColor("#808080"));
                companyTripCostTxt.setTextColor(Color.parseColor("#808080"));

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            String companyOverview = snapshot.child("companyOverview").getValue().toString();
                            companyDataTxt.setText(companyOverview);

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        companyDetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v2.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);

                companyDetailsTxt.setTextColor(getResources().getColor(R.color.purple_500));
                companyOverviewTxt.setTextColor(Color.parseColor("#808080"));
                companyReviewsTxt.setTextColor(Color.parseColor("#808080"));
                companyTripCostTxt.setTextColor(Color.parseColor("#808080"));

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            String companyDetails = snapshot.child("companyDetails").getValue().toString();
                            companyDataTxt.setText(companyDetails);

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        companyReviewsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v3.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);

                companyReviewsTxt.setTextColor(getResources().getColor(R.color.purple_500));
                companyOverviewTxt.setTextColor(Color.parseColor("#808080"));
                companyDetailsTxt.setTextColor(Color.parseColor("#808080"));
                companyTripCostTxt.setTextColor(Color.parseColor("#808080"));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            String companyReviews = snapshot.child("companyReview").getValue().toString();
                            companyDataTxt.setText(companyReviews);

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        companyTripCostTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v4.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);

                companyTripCostTxt.setTextColor(getResources().getColor(R.color.purple_500));
                companyOverviewTxt.setTextColor(Color.parseColor("#808080"));
                companyDetailsTxt.setTextColor(Color.parseColor("#808080"));
                companyReviewsTxt.setTextColor(Color.parseColor("#808080"));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            String companyTripCost = snapshot.child("companyTripCost").getValue().toString();
                            companyDataTxt.setText(companyTripCost);

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {

                    String companyName = snapshot.child("companyName").getValue().toString();
                    String companyLogo = snapshot.child("companyLogo").getValue().toString();
                    String companyTripDuration = snapshot.child("companyTripDuration").getValue().toString();
                    String companyTripDistance = snapshot.child("companyTripDistance").getValue().toString();
                    String companyOverview = snapshot.child("companyOverview").getValue().toString();

                    companyDataTxt.setText(companyOverview);
                    companyTripDurationTxt.setText(companyTripDuration);
                    companyTripDistanceTxt.setText(companyTripDistance);
                    Glide.with(getApplicationContext()).load(companyLogo).into(companyImage);
                    companyNameTxt.setText(companyName);

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CompanyDetailsActivity.this, MainActivity.class));
    }
}