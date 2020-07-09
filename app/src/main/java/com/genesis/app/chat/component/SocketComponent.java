package com.genesis.app.chat.component;

import com.genesis.app.chat.ChartActivity;
import com.genesis.app.chat.SocketIOHelper;
import com.genesis.app.chat.module.SocketModule;

import dagger.Component;

@Component(modules = {SocketModule.class})
public interface SocketComponent {
    void inject(ChartActivity socketIOHelper);
}
