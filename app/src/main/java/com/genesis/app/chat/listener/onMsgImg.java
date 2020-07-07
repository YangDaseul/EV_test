package com.genesis.app.chat.listener;

import javax.inject.Inject;

public class onMsgImg extends SocketListener {

    @Inject
    public onMsgImg(Runnable proceed) {
        super(proceed);
    }
}
