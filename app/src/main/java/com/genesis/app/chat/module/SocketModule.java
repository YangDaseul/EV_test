package com.genesis.app.chat.module;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

@Module
public class SocketModule {
    private Socket mSocket;
    private String OPT_PATH = "/chat.io";
    private String url;
    public SocketModule(String url){
        this.url = url;
    }

    @Provides
    public Socket getSocket() {
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
}
