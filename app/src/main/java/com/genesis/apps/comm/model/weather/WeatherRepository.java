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

public class WeatherRepository{

    private NetCaller netCaller;

    @Inject
    public WeatherRepository(NetCaller netCaller){
        this.netCaller = netCaller;
    }

    public LiveData<WeatherPointResVO> getWeatherPoint(WeatherPointReqVO reqData){
        final MutableLiveData<WeatherPointResVO> data=new MutableLiveData<>();
        BeanReqParm beanReqParm = new BeanReqParm();
        beanReqParm.setData(new Gson().toJson(reqData));
        beanReqParm.setUrl("");
        beanReqParm.setType(METHOD_POST);
        beanReqParm.setCallback(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                data.setValue(new Gson().fromJson(object, WeatherPointResVO.class));
            }

            @Override
            public void onFail(NetResult e) {

            }

            @Override
            public void onError(NetResult e) {

            }
        });
        netCaller.sendData(beanReqParm);
        return data;
    }

}