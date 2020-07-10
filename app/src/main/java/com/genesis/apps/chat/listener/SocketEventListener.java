package com.genesis.apps.chat.listener;

public interface SocketEventListener {
    void onConnect();
    void onConnectError();
    void onDisconnect();
    void onMsg();
    void onMsgImg();
    void onMsgInfo();
    void onTimeout();
}
