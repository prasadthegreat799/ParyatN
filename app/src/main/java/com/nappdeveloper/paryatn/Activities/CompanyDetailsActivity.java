package com.nappdeveloper.paryatn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nappdeveloper.paryatn.MainActivity;
import com.nappdeveloper.paryatn.R;

public class CompanyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CompanyDetailsActivity.this, MainActivity.class));
    }
}