package com.genesis.app.comm;

import dagger.Module;
import dagger.Provides;

@Module
public class ExecutorServiceModule {
    private String className;

    public ExecutorServiceModule(String className){
        this.className = className;
    }

    @Provides
    public ExecutorService getExcutorService() {
        return new ExecutorService(className);
    }
}
