package com.genesis.apps.comm.module;

import android.app.Application;

import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.ga.CCSP;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.comm.util.PreferenceUtil;

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
    public DatabaseHolder provideDatabaseHolder(Application application){
        DatabaseHolder databaseHolder = new DatabaseHolder();
        databaseHolder.openDatabase(application);
        return databaseHolder;
    }

    @Provides
    @Singleton
    public PreferenceUtil provideSharedPref(Application application){
        return new PreferenceUtil(application);
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
