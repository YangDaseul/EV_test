package com.genesis.apps.comm.ui.module;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.genesis.apps.comm.scopes.ActivityScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Application application;

    public ActivityModule(Application application){
        this.application = application;
    }
    @Provides
    @ActivityScope
    SharedPreferences provideSharedPref(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
