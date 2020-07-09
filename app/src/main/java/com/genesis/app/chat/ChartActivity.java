package com.genesis.app.chat;

import android.os.Bundle;

import com.genesis.app.R;
import com.genesis.app.chat.component.DaggerSocketComponent;
import com.genesis.app.chat.listener.SocketEventListener;
import com.genesis.app.chat.module.SocketModule;
import com.genesis.app.comm.ui.BaseActivity;

import javax.inject.Inject;

public class ChartActivity extends BaseActivity implements SocketEventListener {

    @Inject
    SocketIOHelper socketIOHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerSocketComponent.builder().socketModule(new SocketModule(this)).build().inject(this);

        try{
            socketIOHelper.getSocket("");
            socketIOHelper.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketIOHelper.disConnect();
    }

    @Override
    public void onConnect() {
    }

    @Override
    public void onConnectError() {
    }

    @Override
    public void onDisconnect() {
    }

    @Override
    public void onMsg() {
    }

    @Override
    public void onMsgImg() {
    }

    @Override
    public void onMsgInfo() {
    }

    @Override
    public void onTimeout() {
    }
}
