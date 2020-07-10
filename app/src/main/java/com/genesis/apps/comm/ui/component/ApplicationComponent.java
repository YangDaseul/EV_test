package com.genesis.apps.comm.ui.component;

import com.genesis.apps.comm.ui.MyApplication;
import com.genesis.apps.comm.ui.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(MyApplication myApplication);
}
