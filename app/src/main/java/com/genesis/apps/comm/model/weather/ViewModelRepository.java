package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Type;

public class ViewModelRepository<Q extends BaseData, S extends BaseData> {
    private final TypeToken<S> typeToken = new TypeToken<S>(getClass()){};
    private final Type type2 = typeToken.getType();
    public NetCaller netCaller;

    public ViewModelRepository(NetCaller netCaller){
        this.netCaller = netCaller;
    }
    public LiveData<NetUIResponse<S>> reqData(Q reqData, String url, String type){
        final MutableLiveData<NetUIResponse<S>> data=new MutableLiveData<>();


        BeanReqParm beanReqParm = new BeanReqParm();
        beanReqParm.setData(new Gson().toJson(reqData));
        beanReqParm.setUrl(url);
        beanReqParm.setType(type);
        beanReqParm.setCallback(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                S receiveData =  new Gson().fromJson(object, type2);
                data.setValue(NetUIResponse.success(receiveData));

//                        data.setValue(new Gson().fromJson(object, resClassName));
            }

            @Override
            public void onFail(NetResult e) {
                data.setValue(NetUIResponse.error(e.getMseeage(),null));
            }

            @Override
            public void onError(NetResult e) {
                data.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
        });
        netCaller.sendData(beanReqParm);
        return data;
    }


    public MutableLiveData<NetUIResponse<S>> reqDataTest(Q reqData, String url, String type){
        final MutableLiveData<NetUIResponse<S>> data=new MutableLiveData<>();

        BeanReqParm beanReqParm = new BeanReqParm();
        beanReqParm.setData(new Gson().toJson(reqData));
        beanReqParm.setUrl(url);
        beanReqParm.setType(type);
        beanReqParm.setCallback(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

                S receiveData =  new Gson().fromJson(object,  type2);
                data.setValue(NetUIResponse.success(receiveData));
//                        data.setValue(new Gson().fromJson(object, resClassName));
            }

            @Override
            public void onFail(NetResult e) {
                data.setValue(NetUIResponse.error(e.getMseeage(),null));
            }

            @Override
            public void onError(NetResult e) {
                data.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
        });
        beanReqParm.getCallback().onSuccess(new Gson().toJson(reqData));
        return data;
    }

//    public LiveData<S> reqDataFromToServer(Q reqData, String url, String type){
//        final MutableLiveData<S> data=new MutableLiveData<>();
//
//        BeanReqParm beanReqParm = new BeanReqParm();
//        beanReqParm.setData(new Gson().toJson(reqData));
//        beanReqParm.setUrl(url);
//        beanReqParm.setType(type);
//        beanReqParm.setCallback(new NetResultCallback() {
//            @Override
//            public void onSuccess(String object) {
//                data.setValue(new Gson().fromJson(object, resClassName));
//            }
//
//            @Override
//            public void onFail(NetResult e) {
//
//            }
//
//            @Override
//            public void onError(NetResult e) {
//
//            }
//        });
//        netCaller.sendData(beanReqParm);
//        return data;
//    }

}
