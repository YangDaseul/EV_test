package com.genesis.apps.chat;

import android.os.Bundle;

import com.genesis.apps.R;
import com.genesis.apps.chat.listener.SocketEventListener;
import com.genesis.apps.ui.common.activity.BaseActivity;

import javax.inject.Inject;

public class ChartActivity extends BaseActivity implements SocketEventListener {

    @Inject
    public SocketIOHelper socketIOHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DaggerSocketComponent.builder().socketModule(new SocketModule(this)).build().inject(this);

        try{
            socketIOHelper.getSocket("");
            socketIOHelper.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
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
