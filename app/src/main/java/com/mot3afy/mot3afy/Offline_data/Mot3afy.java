package com.mot3afy.mot3afy.Offline_data;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Hind on 10/23/2017.
 */

public class Mot3afy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
