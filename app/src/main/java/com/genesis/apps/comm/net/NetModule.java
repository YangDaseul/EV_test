package com.genesis.apps.comm.net;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetModule {
    @Provides
    @Singleton
    public NetCaller getNetCaller() {
        return new NetCaller();
    }
}
