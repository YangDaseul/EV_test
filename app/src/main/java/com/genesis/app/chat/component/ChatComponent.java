package com.genesis.app.chat.component;

import com.genesis.app.chat.SocketIOHelper;
import com.genesis.app.chat.module.SocketListenerModule;
import com.genesis.app.chat.module.SocketModule;

import dagger.Component;

@Component(modules = {SocketModule.class, SocketListenerModule.class})
public interface ChatComponent {
//    void inject(SocketIOHelper socketIOHelper);//??
}
