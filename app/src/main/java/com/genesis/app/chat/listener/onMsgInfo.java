package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onMsgInfo extends SocketListener {

    @Inject
    public onMsgInfo(Runnable proceed) {
        super(proceed);
    }
}
