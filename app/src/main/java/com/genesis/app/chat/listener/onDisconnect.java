package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onDisconnect extends SocketListener {

    @Inject
    public onDisconnect(Runnable proceed) {
        super(proceed);
    }
}
