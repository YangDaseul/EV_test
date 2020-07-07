package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onConnectError extends SocketListener {

    @Inject
    public onConnectError(Runnable proceed) {
        super(proceed);
    }
}
