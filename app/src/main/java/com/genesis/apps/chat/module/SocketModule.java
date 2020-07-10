package com.genesis.apps.chat.module;

import com.genesis.apps.chat.SocketIOHelper;
import com.genesis.apps.chat.listener.SocketEventListener;

import dagger.Module;
import dagger.Provides;

@Module
public class SocketModule {
    private SocketEventListener socketEventListener;

    public SocketModule(SocketEventListener socketEventListener){
        this.socketEventListener = socketEventListener;
    }

//    @Provides
//    public SocketEventListener getListener(SocketEventListener socketEventListener) {
//        return socketEventListener;
//    }

    @Provides
    public SocketIOHelper getChatSocket() {
        return new SocketIOHelper(socketEventListener);
    }
}
