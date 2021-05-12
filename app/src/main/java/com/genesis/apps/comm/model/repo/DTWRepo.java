package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.DTW_1001;
import com.genesis.apps.comm.model.api.gra.DTW_1003;
import com.genesis.apps.comm.model.api.gra.DTW_1004;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DTWRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<DTW_1001.Response>> RES_DTW_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DTW_1003.Response>> RES_DTW_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DTW_1004.Response>> RES_DTW_1004 = new MutableLiveData<>();


    @Inject
    public DTWRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<DTW_1001.Response>> REQ_DTW_1001(final DTW_1001.Request reqData) {
        RES_DTW_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DTW_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, DTW_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DTW_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DTW_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DTW_1001, reqData);

        return RES_DTW_1001;
    }

    public MutableLiveData<NetUIResponse<DTW_1003.Response>> REQ_DTW_1003(final DTW_1003.Request reqData) {
        RES_DTW_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DTW_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, DTW_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DTW_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DTW_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DTW_1003, reqData);

        return RES_DTW_1003;
    }

    public MutableLiveData<NetUIResponse<DTW_1004.Response>> REQ_DTW_1004(final DTW_1004.Request reqData) {
        RES_DTW_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DTW_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, DTW_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DTW_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DTW_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DTW_1004, reqData);

        return RES_DTW_1004;
    }

}
