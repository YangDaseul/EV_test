package com.genesis.app.chat;

import com.genesis.app.chat.component.ChatComponent;
import com.genesis.app.chat.listener.SocketListener;
import com.genesis.app.chat.listener.onConnect;
import com.genesis.app.chat.listener.onConnectError;
import com.genesis.app.chat.listener.onDisconnect;
import com.genesis.app.chat.listener.onMsg;
import com.genesis.app.chat.listener.onMsgImg;
import com.genesis.app.chat.listener.onMsgInfo;
import com.genesis.app.chat.listener.onTimeout;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.socket.client.Socket;

@Singleton
public class SocketIOHelper {
    private Socket socket;
    private String EVENT_KEY_MSG="msg";
    private String EVENT_KEY_MSGINFO="msgInfo";
    private String EVENT_KEY_MSGIMG="msgImg";
    private onConnect onConnectListener;
    private onConnectError onConnectErrorListener;
    private onDisconnect onDisconnectListener;
    private onMsg onMsgListener;
    private onMsgImg onMsgImgListener;
    private onMsgInfo onMsgInfoListener;
    private onTimeout onTimeoutListener;

    @Inject
    public SocketIOHelper(Socket socket, onConnect onConnectListener, onConnectError onConnectErrorListener, onDisconnect onDisconnectListener, onMsg onMsgListener, onMsgImg onMsgImgListener,onMsgInfo onMsgInfoListener,onTimeout onTimeoutListener){
        this.socket = socket;
        this.onConnectListener = onConnectListener;
        this.onConnectErrorListener = onConnectErrorListener;
        this.onDisconnectListener = onDisconnectListener;
        this.onMsgListener = onMsgListener;
        this.onMsgImgListener = onMsgImgListener;
        this.onMsgInfoListener = onMsgInfoListener;
        this.onTimeoutListener = onTimeoutListener;
    }

    public void connect(){
        if(socket!=null){
            socket.on(Socket.EVENT_CONNECT, onConnectListener);
            socket.on(Socket.EVENT_DISCONNECT,onDisconnectListener);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectErrorListener);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onTimeoutListener);
            socket.on(EVENT_KEY_MSG, onMsgListener);
            socket.on(EVENT_KEY_MSGINFO, onMsgInfoListener);
            socket.on(EVENT_KEY_MSGIMG, onMsgImgListener);
            socket.connect();
        }
    }

    public void disConnect(){
        if(socket!=null){
            socket.disconnect();
            socket.off(Socket.EVENT_CONNECT, onConnectListener);
            socket.off(Socket.EVENT_DISCONNECT,onDisconnectListener);
            socket.off(Socket.EVENT_CONNECT_ERROR, onConnectErrorListener);
            socket.off(Socket.EVENT_CONNECT_TIMEOUT, onTimeoutListener);
            socket.off(EVENT_KEY_MSG, onMsgListener);
            socket.off(EVENT_KEY_MSGINFO, onMsgInfoListener);
            socket.off(EVENT_KEY_MSGIMG, onMsgImgListener);
        }
    }

}
