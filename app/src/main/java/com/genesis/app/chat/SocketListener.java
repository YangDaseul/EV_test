package com.genesis.app.chat;

import javax.inject.Inject;

import io.socket.emitter.Emitter;

public class SocketListener implements SocketEventListener {
    private Emitter.Listener listener;
    private String type;
    private Runnable proceed;

    @Inject
    public SocketListener(Emitter.Listener listener, String type, Runnable proceed){
        this.listener = listener;
        this.type=type;
        this.proceed = proceed;
    }

    @Override
    public void proceed() {
        if(proceed!=null){
            proceed.run();
        }
    }
}
