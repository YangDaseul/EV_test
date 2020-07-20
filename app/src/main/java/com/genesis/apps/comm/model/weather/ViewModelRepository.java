package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModelRepository<Q extends BaseData, S extends BaseData> {

    public NetCaller netCaller;
    public Class<S> resClassName;

    public ViewModelRepository(NetCaller netCaller){
        this.netCaller = netCaller;
    }

    public LiveData<S> getWeatherPoint(Q reqData, String url, String type){
        final MutableLiveData<S> data=new MutableLiveData<>();

        BeanReqParm beanReqParm = new BeanReqParm();
        beanReqParm.setData(new Gson().toJson(reqData));
        beanReqParm.setUrl(url);
        beanReqParm.setType(type);
        beanReqParm.setCallback(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                data.setValue(new Gson().fromJson(object, resClassName));
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
