package com.genesis.apps.chat;

import com.genesis.apps.chat.listener.SocketEventListener;
import com.genesis.apps.chat.listener.SocketListener;

import java.net.URISyntaxException;

import javax.inject.Singleton;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;

@Singleton
public class SocketIOHelper {
    private Socket socket;
    private String url;

    private String OPT_PATH = "/chat.io";
    private String EVENT_KEY_MSG="msg";
    private String EVENT_KEY_MSGINFO="msgInfo";
    private String EVENT_KEY_MSGIMG="msgImg";

    private SocketListener onConnect;
    private SocketListener onDisconnect;
    private SocketListener onConnectError;
    private SocketListener onMsg;
    private SocketListener onMsgInfo;
    private SocketListener onMsgImg;
    private SocketListener onTimeout;

    public SocketIOHelper(){
    }

    public void initSocketHelper(SocketEventListener socketEventListener){
        this.onConnect = new SocketListener(() -> socketEventListener.onConnect());
        this.onDisconnect = new SocketListener(() -> socketEventListener.onDisconnect());
        this.onConnectError = new SocketListener(() -> socketEventListener.onConnectError());
        this.onMsg = new SocketListener(() -> socketEventListener.onMsg());
        this.onMsgInfo = new SocketListener(() -> socketEventListener.onMsgInfo());
        this.onMsgImg = new SocketListener(() -> socketEventListener.onMsgImg());
        this.onTimeout = new SocketListener(() -> socketEventListener.onTimeout());
    }

    public void getSocket(String url) throws Exception {
        try {
            this.url = url;
            IO.Options options = new IO.Options();
            options.query=url.substring(url.indexOf("?")+1);
            url = url.substring(0,url.indexOf("?"));
            options.path = OPT_PATH;
            options.transports = new String[]{WebSocket.NAME};
            options.forceNew=true;
            options.reconnection=true;
            socket = IO.socket(url, options);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e1){
            throw new Exception(e1);
        }
    }

    public void connect(){
        if(socket!=null){
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onTimeout);
            socket.on(EVENT_KEY_MSG, onMsg);
            socket.on(EVENT_KEY_MSGINFO, onMsgInfo);
            socket.on(EVENT_KEY_MSGIMG, onMsgImg);
            socket.connect();
        }
    }

    public void disConnect(){
        if(socket!=null){
            socket.disconnect();
            socket.off(Socket.EVENT_CONNECT, onConnect);
            socket.off(Socket.EVENT_DISCONNECT,onDisconnect);
            socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.off(Socket.EVENT_CONNECT_TIMEOUT, onTimeout);
            socket.off(EVENT_KEY_MSG, onMsg);
            socket.off(EVENT_KEY_MSGINFO, onMsgInfo);
            socket.off(EVENT_KEY_MSGIMG, onMsgImg);
        }
    }

}
