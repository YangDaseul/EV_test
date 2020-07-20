package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.net.NetCaller;

import javax.inject.Inject;

public class WeatherRepository{

    private NetCaller netCaller;

    @Inject
    public WeatherRepository(NetCaller netCaller){
        this.netCaller = netCaller;
    }

}
