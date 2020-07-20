package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.genesis.apps.comm.net.HttpRequest.METHOD_POST;

public class WeatherRepository2 extends ViewModelRepository<WeatherPointReqVO, WeatherPointResVO>{

    @Inject
    public WeatherRepository2(NetCaller netCaller) {
        super(netCaller);
    }

}
