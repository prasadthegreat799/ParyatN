package com.nappdeveloper.paryatn.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nappdeveloper.paryatn.MainActivity;
import com.nappdeveloper.paryatn.R;

import java.util.HashMap;

public class signInActivity extends AppCompatActivity {

    //Declaration
    GoogleSignInClient mSignInClient;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressBar;
    Button signinBtn;

    private static final int CREDENTIAL_PICKER_REQUEST = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Initialization
        firebaseAuth = FirebaseAuth.getInstance();
        signinBtn = findViewById(R.id.signInBtn);


        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Please Wait...");
        progressBar.setMessage("We are setting Everything for you...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();


        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("569501515926-vh5ca5e59doe2b5qbh0gij7e5j42gus5.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(getApplicationContext(), signInOptions);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable(view.getContext())) {
                    Intent intent = mSignInClient.getSignInIntent();
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(getApplicationContext(), "Please,Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100) {
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if (googleSignInAccountTask.isSuccessful()) {
                //progressBar.show();
                try {
                    GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult(ApiException.class);

                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(), null);

                        String email = googleSignInAccount.getEmail();

                        //check email already exist or not.
                        firebaseAuth.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                                        if (isNewUser) {

                                            firebaseAuth.signInWithCredential(authCredential)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull @com.google.firebase.database.annotations.NotNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {

                                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                DatabaseReference myRef = database.getReference();
                                                                HashMap<String, String> user_details = new HashMap<>();

                                                                String id = googleSignInAccount.getId().toString();
                                                                String name = googleSignInAccount.getDisplayName().toString();
                                                                String mail = googleSignInAccount.getEmail().toString();
                                                                String pic = googleSignInAccount.getPhotoUrl().toString();


                                                                user_details.put("userId", id);
                                                                user_details.put("userName", name);
                                                                user_details.put("userNameLowerCase", name.toLowerCase());
                                                                user_details.put("mail", mail);
                                                                user_details.put("profilePic", pic);


                                                                myRef.child("users").child(id).setValue(user_details)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Intent intent = new Intent(getApplicationContext(), registerUserActivity.class);
                                                                                    startActivity(intent);
                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please,Try again", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });

                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Please,Try again", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            firebaseAuth.signInWithCredential(authCredential)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                                                            if (task.isSuccessful()) {

                                                                //progressBar.cancel();

                                                                String welcome = "WelCome Back " + googleSignInAccount.getGivenName() + " ...";
                                                                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);

                                                            }


                                                        }
                                                    });
                                        }

                                    }
                                });

                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //checking user already logined or not
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            //if user not signed in then do nothing
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}