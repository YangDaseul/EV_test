package com.genesis.apps.comm.model;


import com.genesis.apps.comm.model.weather.ViewModelRepository;
import com.genesis.apps.comm.net.NetCaller;

import javax.inject.Inject;

public class ExampleRepository extends ViewModelRepository<ExampleReqVO,ExampleResVO> {

    @Inject
    public ExampleRepository(NetCaller netCaller) {
        super(netCaller);
    }




}