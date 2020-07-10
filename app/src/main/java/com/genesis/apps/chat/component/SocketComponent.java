package com.genesis.apps.chat.component;

import com.genesis.apps.chat.ChartActivity;
import com.genesis.apps.chat.module.SocketModule;

import dagger.Component;

@Component(modules = {SocketModule.class})
public interface SocketComponent {
    void inject(ChartActivity socketIOHelper);
}
