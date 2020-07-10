package com.genesis.apps.comm.ui;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.genesis.apps.comm.ui.component.DaggerApplicationComponent;
import com.genesis.apps.comm.ui.module.ApplicationModule;

import javax.inject.Inject;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    @Inject
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build().inject(this);
        Log.e("check","onCreate application   " + sharedPreferences);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Log.e("check","onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.e("check","onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.e("check","onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.e("check","onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.e("check","onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        Log.e("check","onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.e("check","onActivityDestroyed");
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
