package com.nappdeveloper.paryatn.OfflineCapability;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Offlinefirebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
