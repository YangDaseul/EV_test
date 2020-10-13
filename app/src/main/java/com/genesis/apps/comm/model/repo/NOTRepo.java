package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.NOT_0001;
import com.genesis.apps.comm.model.gra.api.NOT_0002;
import com.genesis.apps.comm.model.gra.api.NOT_0003;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class NOTRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<NOT_0001.Response>> RES_NOT_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<NOT_0002.Response>> RES_NOT_0002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<NOT_0003.Response>> RES_NOT_0003 = new MutableLiveData<>();
    
    @Inject
    public NOTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<NOT_0001.Response>> REQ_NOT_0001(final NOT_0001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_NOT_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0001, reqData);

        return RES_NOT_0001;
    }


    public MutableLiveData<NetUIResponse<NOT_0002.Response>> REQ_NOT_0002(final NOT_0002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0002.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_NOT_0002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0002, reqData);

        return RES_NOT_0002;
    }


    public MutableLiveData<NetUIResponse<NOT_0003.Response>> REQ_NOT_0003(final NOT_0003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0003.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_NOT_0003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0003, reqData);

        return RES_NOT_0003;
    }


}
