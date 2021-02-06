package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.EVL_1001;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class EVLRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<EVL_1001.Response>> RES_EVL_1001 = new MutableLiveData<>();

    @Inject
    public EVLRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<EVL_1001.Response>> REQ_EVL_1001(final EVL_1001.Request reqData) {
        RES_EVL_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EVL_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, EVL_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EVL_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EVL_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EVL_1001, reqData);

        return RES_EVL_1001;
    }
}
