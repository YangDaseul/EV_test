package com.genesis.apps.comm.ui;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    @Inject
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
//        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build().inject(this);
        Log.e("check","onCreate application   " + sharedPreferences);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
