package com.genesis.apps.comm;

import com.genesis.apps.comm.util.UiThreadExecutor;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;




public class ExecutorService {
    private ScheduledExecutorService executorService;
    private ListeningExecutorService listeningExecutorService;
    private UiThreadExecutor uiThreadExecutor = new UiThreadExecutor();

    public ExecutorService(String className){
        executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat(className).build());
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    }

    public ListeningExecutorService getListeningExecutorService() {
        return listeningExecutorService;
    }
    public UiThreadExecutor getUiThreadExecutor() {
        return uiThreadExecutor;
    }

    public void shutDownExcutor() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
