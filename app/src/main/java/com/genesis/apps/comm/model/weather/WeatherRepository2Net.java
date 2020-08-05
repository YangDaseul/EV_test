package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetViewModelRepository;

import javax.inject.Inject;

public class WeatherRepository2Net extends NetViewModelRepository<WeatherPointReqVO, WeatherPointResVO> {

    @Inject
    public WeatherRepository2Net(NetCaller netCaller) {
        super(netCaller);
    }

}
