package com.genesis.apps.comm.model;


import com.genesis.apps.comm.net.NetViewModelRepository;
import com.genesis.apps.comm.net.NetCaller;

import javax.inject.Inject;

public class ExampleRepositoryNet extends NetViewModelRepository<ExampleReqVO,ExampleResVO> {

    @Inject
    public ExampleRepositoryNet(NetCaller netCaller) {
        super(netCaller);
    }




}