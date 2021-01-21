package com.genesis.apps.comm.module;

import android.app.Application;

import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.net.HttpRequestUtil;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.ga.CCSP;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.PreferenceUtil;
import com.genesis.apps.comm.util.ScreenCaptureUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.DatabaseHolder;
import com.hmns.playmap.network.PlayMapRestApi;

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
    public ExecutorService getExcutorService(Application application) {
        return new ExecutorService(application.getClass().getSimpleName());
    }

    @Provides
    public HttpRequestUtil getHttpRequestUtil() {
        return new HttpRequestUtil();
    }

    @Provides
    public NetCaller getNetCaller(HttpRequestUtil httpRequestUtil, GA ga) {
        return new NetCaller(httpRequestUtil,ga);
    }

    @Provides
    @Singleton
    public CCSP getCCSP(HttpRequestUtil httpRequestUtil, Application application, LoginInfoDTO loginInfoDTO){
        return new CCSP(httpRequestUtil, application, loginInfoDTO);
    }

    @Provides
    @Singleton
    public GA getGA(HttpRequestUtil httpRequestUtil, LoginInfoDTO loginInfoDTO){
        return new GA(httpRequestUtil, loginInfoDTO);
    }

    @Provides
    @Singleton
    public PlayMapRestApi providePlayMapRestApi(Application application){
        PlayMapRestApi playMapRestApi = new PlayMapRestApi(application);
        playMapRestApi.setPlayMapApiKey("QW5pWFZMQjdVVzZKRmpmUTlzVUc6NlhxNTMzNTFoME0wenhXdHJHVXg=");
        return playMapRestApi;
    }

    @Provides
    public ScreenCaptureUtil getScreenCaptureUtil(Application context){
        return new ScreenCaptureUtil(context);
    }

    @Provides
    @Singleton
    public DeviceDTO provideDeviceInfo(Application application){
        return new DeviceDTO(application);
    }


    @Provides
    @Singleton
    public LoginInfoDTO provideLoginInfo(){

        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();

        if(loginInfoDTO.loadLoginInfo()!=null){
            loginInfoDTO = loginInfoDTO.loadLoginInfo();
        }

        return loginInfoDTO;
    }
}
