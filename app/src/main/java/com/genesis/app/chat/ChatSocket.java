package com.genesis.app.chat;

import java.net.URISyntaxException;

import dagger.Module;
import dagger.Provides;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

@Module
public class ChatSocket {
    private Socket mSocket;
    private String OPT_PATH = "/chat.io";
    private String EVENT_KEY_MSG="msg";
    private String EVENT_KEY_MSGINFO="msgInfo";
    private String EVENT_KEY_MSGIMG="msgImg";

    private Emitter.Listener onConnect=null;
    private Emitter.Listener onDisconnect=null;
    private Emitter.Listener onConnectError=null;
    private Emitter.Listener onTimeout=null;
    private Emitter.Listener msg=null;
    private Emitter.Listener msgInfo=null;
    private Emitter.Listener msgImg=null;

    public ChatSocket(Emitter.Listener onConnect, Emitter.Listener onDisconnect, Emitter.Listener onConnectError, Emitter.Listener onTimeout, Emitter.Listener msg, Emitter.Listener msgInfo,Emitter.Listener msgImg){
        this.onConnect = onConnect;
        this.onDisconnect = onDisconnect;
        this.onConnectError = onConnectError;
        this.onTimeout = onTimeout;
        this.msg = msg;
        this.msgInfo = msgInfo;
        this.msgImg = msgImg;
    }

    @Provides
    public Socket getSocket(String url) {
        try {
            IO.Options options = new IO.Options();
            options.query=url.substring(url.indexOf("?")+1);
            url = url.substring(0,url.indexOf("?"));
            options.path = OPT_PATH;
            options.transports = new String[]{WebSocket.NAME};
            options.forceNew=true;
            options.reconnection=true;
            mSocket = IO.socket(url, options);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return mSocket;
    }

    @Provides
    public void connect(){
        if(mSocket!=null){
            mSocket.on(Socket.EVENT_CONNECT,onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onTimeout);
            mSocket.on(EVENT_KEY_MSG, msg);
            mSocket.on(EVENT_KEY_MSGINFO, msgInfo);
            mSocket.on(EVENT_KEY_MSGIMG, msgImg);
            mSocket.connect();
        }
    }

    @Provides
    public void disConnect(){
        if(mSocket!=null){
            mSocket.disconnect();
            mSocket.off(Socket.EVENT_CONNECT,onConnect);
            mSocket.off(Socket.EVENT_DISCONNECT,onDisconnect);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onTimeout);
            mSocket.off(EVENT_KEY_MSG, msg);
            mSocket.off(EVENT_KEY_MSGINFO, msgInfo);
            mSocket.off(EVENT_KEY_MSGIMG, msgImg);
        }
    }

}
