package com.genesis.apps.comm.net;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetModule.class})
@Singleton
public interface NetComponent {
    NetCaller maker();
}
