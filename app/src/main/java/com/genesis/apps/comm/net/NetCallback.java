package com.genesis.apps.comm.net;

public interface NetCallback {
    void onSuccess(Object object);
    void onFail(NetResult e);
    void onError(NetResult e);
}
