package com.genesis.apps.comm.module;

import android.app.Activity;

import com.genesis.apps.chat.SocketIOHelper;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.ui.activity.BaseActivity;

import javax.annotation.Nullable;

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
    public ExecutorService getExcutorService(Activity context) {
        return new ExecutorService(context.getClass().getSimpleName());
    }

    @Provides
    public BaseActivity getBaseActivity(Activity activity){
        return (BaseActivity)activity;
    }
}
