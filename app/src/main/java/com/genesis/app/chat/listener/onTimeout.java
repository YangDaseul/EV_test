package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onTimeout extends SocketListener {

    @Inject
    public onTimeout(Runnable proceed) {
        super(proceed);
    }
}
