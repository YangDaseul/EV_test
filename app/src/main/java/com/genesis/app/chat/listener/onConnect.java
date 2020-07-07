package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onConnect extends SocketListener {

    @Inject
    public onConnect(Runnable proceed) {
        super(proceed);
    }
}
