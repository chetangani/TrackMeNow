package com.chetangani.trackmenow;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Chetan G on 6/26/2016.
 */
public class TrackMeNow extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
