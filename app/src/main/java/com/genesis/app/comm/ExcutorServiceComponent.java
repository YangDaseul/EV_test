package com.genesis.app.comm;

import dagger.Component;

@Component(modules = {ExecutorServiceModule.class})
public interface ExcutorServiceComponent {
    ExecutorService maker();
}
