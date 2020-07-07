package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onMsg extends SocketListener {

    @Inject
    public onMsg(Runnable proceed) {
        super(proceed);
    }
}
