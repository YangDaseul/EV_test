package com.genesis.apps.comm.module;

import android.app.Activity;
import android.app.Application;

import com.genesis.apps.comm.net.HttpRequestUtil;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.ga.CCSP;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.comm.util.PreferenceUtil;

import javax.inject.Singleton;

import dagger.Binds;
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
    public ExecutorService getExcutorService(Application application) {
        return new ExecutorService(application.getClass().getSimpleName());
    }

    @Provides
    public HttpRequestUtil getHttpRequestUtil() {
        return new HttpRequestUtil();
    }

    @Provides
    public NetCaller getNetCaller(ExecutorService es,HttpRequestUtil httpRequestUtil) {
        return new NetCaller(es,httpRequestUtil);
    }

    @Provides
    @Singleton
    public CCSP getCCSP(HttpRequestUtil httpRequestUtil, Application application){
        return new CCSP(httpRequestUtil, application);
    }

    @Provides
    @Singleton
    public GA getGA(CCSP ccsp, HttpRequestUtil httpRequestUtil){
        return new GA(ccsp, httpRequestUtil);
    }
}
