package com.genesis.apps.comm;

import dagger.Component;

@Component(modules = {ExecutorServiceModule.class})
public interface ExcutorServiceComponent {
    ExecutorService maker();
}
