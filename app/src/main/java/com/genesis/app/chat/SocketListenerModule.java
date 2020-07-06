package com.genesis.app.chat;

import dagger.Module;
import dagger.Provides;
import io.socket.emitter.Emitter;

@Module
public class SocketListenerModule {
    private static String EVENT_CONNECT="onConnect";
    private static String EVENT_DISCONNECT="onDisconnect";
    private static String EVENT_CONNECTERROR="onConnectError";
    private static String EVENT_TIMEOUT="onTimeout";
    private static String EVENT_MSG="msg";
    private static String EVENT_MSGINFO="msgInfo";
    private static String EVENT_MSGIMG="msgImg";

    @Provides
    SocketListener provideOnConnect(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_CONNECT, proceed);
    }

    @Provides
    SocketListener provideOnDisconnect(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_DISCONNECT, proceed);
    }

    @Provides
    SocketListener provideOnConnectError(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_CONNECTERROR, proceed);
    }

    @Provides
    SocketListener provideOnTimeout(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_TIMEOUT, proceed);
    }

    @Provides
    SocketListener provideOnMsg(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_MSG, proceed);
    }

    @Provides
    SocketListener provideOnMsgInfo(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_MSGINFO, proceed);
    }

    @Provides
    SocketListener provideOnMsgImg(Emitter.Listener listener, String type, Runnable proceed){
        return new SocketListener(listener, EVENT_MSGIMG, proceed);
    }
}
