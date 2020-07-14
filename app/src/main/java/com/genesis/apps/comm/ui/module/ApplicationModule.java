package com.genesis.apps.comm.ui.module;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.ga.CCSP;
import com.genesis.apps.comm.net.ga.GA;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class ApplicationModule {

    @Provides
    @Singleton
    public SharedPreferences provideSharedPref(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    public NetCaller getNetCaller() {
        return new NetCaller();
    }


    @Provides
    @Singleton
    public CCSP getCCSP(NetCaller netCaller, Application application){
        return new CCSP(netCaller, application);
    }

    @Provides
    @Singleton
    public GA getGA(CCSP ccsp, NetCaller netCaller){
        return new GA(ccsp, netCaller);
    }
}
