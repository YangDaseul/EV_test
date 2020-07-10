package com.genesis.apps.chat.listener;

import io.socket.emitter.Emitter;

public class SocketListener implements Emitter.Listener{
    private Runnable proceed;

    public SocketListener(Runnable proceed){
        this.proceed = proceed;
    }

    @Override
    public void call(Object... args) {
        if(proceed!=null){
            proceed.run();
        }
    }
}
