package com.genesis.app.chat.module;

import com.genesis.app.chat.SocketIOHelper;
import com.genesis.app.chat.listener.SocketEventListener;
import com.genesis.app.chat.qualifier.QualChatURL;

import javax.inject.Singleton;

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
