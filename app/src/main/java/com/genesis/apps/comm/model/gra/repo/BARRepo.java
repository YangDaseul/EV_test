package com.genesis.apps.comm.model.gra.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BAR_1001;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class BARRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<BAR_1001.Response>> RES_BAR_1001 = new MutableLiveData<>();

    @Inject
    public BARRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<BAR_1001.Response>> REQ_BAR_1001(final BAR_1001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BAR_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, BAR_1001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_BAR_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BAR_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BAR_1001, reqData);

        return RES_BAR_1001;
    }


}
