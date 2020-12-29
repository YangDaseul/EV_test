package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.STO_1001;
import com.genesis.apps.comm.model.api.gra.STO_1002;
import com.genesis.apps.comm.model.api.gra.STO_1003;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class STORepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<STO_1001.Response>> RES_STO_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STO_1002.Response>> RES_STO_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STO_1003.Response>> RES_STO_1003 = new MutableLiveData<>();

    @Inject
    public STORepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<STO_1001.Response>> REQ_STO_1001(final STO_1001.Request reqData) {
        RES_STO_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STO_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, STO_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STO_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_STO_1001.setValue(NetUIResponse.success(TestCode.STO_1001));
            }

            @Override
            public void onError(NetResult e) {
                RES_STO_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STO_1001, reqData);

        return RES_STO_1001;
    }

    public MutableLiveData<NetUIResponse<STO_1002.Response>> REQ_STO_1002(final STO_1002.Request reqData) {
        RES_STO_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STO_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, STO_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STO_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_STO_1002.setValue(NetUIResponse.success(TestCode.STO_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_STO_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STO_1002, reqData);

        return RES_STO_1002;
    }

    public MutableLiveData<NetUIResponse<STO_1003.Response>> REQ_STO_1003(final STO_1003.Request reqData) {
        RES_STO_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STO_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, STO_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STO_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STO_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STO_1003, reqData);

        return RES_STO_1003;
    }


}
