package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.WRT_1001;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class WRTRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<WRT_1001.Response>> RES_WRT_1001 = new MutableLiveData<>();

    @Inject
    public WRTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<WRT_1001.Response>> REQ_WRT_1001(final WRT_1001.Request reqData) {
        RES_WRT_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WRT_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, WRT_1001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WRT_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_WRT_1001.setValue(NetUIResponse.success(TestCode.WRT_1001));
            }

            @Override
            public void onError(NetResult e) {
                RES_WRT_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WRT_1001, reqData);

        return RES_WRT_1001;
    }


}
