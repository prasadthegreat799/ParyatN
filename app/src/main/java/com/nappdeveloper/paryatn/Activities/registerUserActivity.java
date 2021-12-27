package com.nappdeveloper.paryatn.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.MainActivity;
import com.nappdeveloper.paryatn.R;

import java.util.HashMap;

public class registerUserActivity extends AppCompatActivity {

    EditText userCollege,userBranch,userPhone;
    Button registerUserDetailsBtn;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        String userId= GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userBranch=(EditText) findViewById(R.id.user_branch_txt);
        userCollege=(EditText) findViewById(R.id.user_college_txt);
        userPhone=(EditText) findViewById(R.id.user_phone_txt);
        registerUserDetailsBtn=(Button) findViewById(R.id.register_UserDetails_Btn);


        registerUserDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String branch=userBranch.getText().toString().trim();
               String college=userCollege.getText().toString().trim();
               String phone=userPhone.getText().toString().trim();

               if(branch.isEmpty() || college.isEmpty() || phone.isEmpty()){
                   Toast.makeText(getApplicationContext(),"Please,Fill all fields",Toast.LENGTH_SHORT).show();
               }else{
                   register_userDetails(college,branch,phone);
               }

            }
        });
    }

    private void register_userDetails(String college, String branch, String phone) {

        HashMap<String, Object> user_details = new HashMap<>();
        user_details.put("userCollege", college);
        user_details.put("userBranch", branch);
        user_details.put("userPhone", phone);
        user_details.put("userTrips", "0");
        user_details.put("userRating","0");

        databaseReference.updateChildren(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Details Registered Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Error occurred,Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}