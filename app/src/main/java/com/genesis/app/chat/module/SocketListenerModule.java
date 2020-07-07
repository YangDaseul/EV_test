package com.genesis.app.chat.module;

import com.genesis.app.chat.listener.onConnect;
import com.genesis.app.chat.listener.onConnectError;
import com.genesis.app.chat.listener.onDisconnect;
import com.genesis.app.chat.listener.onMsg;
import com.genesis.app.chat.listener.onMsgImg;
import com.genesis.app.chat.listener.onMsgInfo;
import com.genesis.app.chat.listener.onTimeout;

import dagger.Module;
import dagger.Provides;

@Module
public class SocketListenerModule {

    private Runnable[] runnables;

    public SocketListenerModule(Runnable...runnables){
        this.runnables = runnables;
    }

    @Provides
    onConnect provideOnConnect() {
        return new onConnect(runnables[0]);
    }

    @Provides
    onDisconnect provideOnDisconnect() {
        return new onDisconnect(runnables[1]);
    }

    @Provides
    onConnectError provideOnConnectError() {
        return new onConnectError(runnables[2]);
    }

    @Provides
    onTimeout provideOnTimeout() {
        return new onTimeout(runnables[3]);
    }

    @Provides
    onMsg provideOnMsg() {
        return new onMsg(runnables[4]);
    }

    @Provides
    onMsgInfo provideOnMsgInfo() {
        return new onMsgInfo(runnables[5]);
    }

    @Provides
    onMsgImg provideOnMsgImg() {
        return new onMsgImg(runnables[6]);
    }
}
