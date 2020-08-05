package com.genesis.apps.comm.module;

import android.app.Activity;

import com.genesis.apps.chat.SocketIOHelper;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.ui.activity.BaseActivity;
import com.hmns.playmap.network.PlayMapRestApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {
    @Provides
    public SocketIOHelper getChatSocket() {
        return new SocketIOHelper();
    }

    @Provides
    public BaseActivity getBaseActivity(Activity activity){
        return (BaseActivity)activity;
    }
}
