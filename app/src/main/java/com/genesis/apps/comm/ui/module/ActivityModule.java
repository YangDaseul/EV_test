package com.genesis.apps.comm.ui.module;

import android.app.Activity;

import com.genesis.apps.chat.SocketIOHelper;
import com.genesis.apps.comm.util.excutor.ExecutorService;

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
}
