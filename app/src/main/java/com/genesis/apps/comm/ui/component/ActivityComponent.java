package com.genesis.apps.comm.ui.component;

import com.genesis.apps.comm.scopes.ActivityScope;
import com.genesis.apps.comm.ui.MyApplication;
import com.genesis.apps.comm.ui.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

//@Component(modules = {ApplicationModule.class}, dependencies = ApplicationComponent.class)
@Component(modules = {ApplicationModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MyApplication myApplication);
}
