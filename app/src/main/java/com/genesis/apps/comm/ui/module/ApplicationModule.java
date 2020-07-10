package com.genesis.apps.comm.ui.module;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }
    @Provides
    @Singleton
    SharedPreferences provideSharedPref(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
