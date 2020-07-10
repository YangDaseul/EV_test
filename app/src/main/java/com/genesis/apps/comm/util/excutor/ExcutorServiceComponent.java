package com.genesis.apps.comm.util.excutor;

import dagger.Component;

@Component(modules = {ExecutorServiceModule.class})
public interface ExcutorServiceComponent {
    ExecutorService maker();
}
