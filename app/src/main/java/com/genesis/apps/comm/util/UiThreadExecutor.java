package com.genesis.apps.comm.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class UiThreadExecutor implements Executor {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

    @Override
    protected void finalize() throws Throwable {
        mHandler = null;
        super.finalize();
    }
}