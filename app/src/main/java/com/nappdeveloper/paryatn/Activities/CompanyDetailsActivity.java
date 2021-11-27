package com.nappdeveloper.paryatn.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nappdeveloper.paryatn.MainActivity;
import com.nappdeveloper.paryatn.R;

public class CompanyDetailsActivity extends AppCompatActivity {

    ImageView companyImage;
    TextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);


        companyImage=(ImageView) findViewById(R.id.CompanyLogoImg);
        companyName=(TextView) findViewById(R.id.companyNameTxt);

        String imageId = getIntent().getStringExtra("imageId");
        String imageCategory = getIntent().getStringExtra("imageCategory");

        FirebaseDatabase.getInstance().getReference().child("filterCompanies").child(imageCategory).child(imageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (getApplicationContext()== null) {
                    return;
                }
                String name = snapshot.child("companyName").getValue().toString();
                String imageLink = snapshot.child("companyLogo").getValue().toString();


                Glide.with(getApplicationContext()).load(imageLink).into(companyImage);
                companyName.setText(name);

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