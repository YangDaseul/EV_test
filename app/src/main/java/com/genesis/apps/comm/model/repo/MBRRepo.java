package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.MBR_0001;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class MBRRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<MBR_0001.Response>> RES_MBR_0001 = new MutableLiveData<>();

    @Inject
    public MBRRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<MBR_0001.Response>> REQ_MBR_0001(final MBR_0001.Request reqData) {
        RES_MBR_0001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MBR_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, MBR_0001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MBR_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_MBR_0001.setValue(NetUIResponse.success(TestCode.MBR_0001));
            }

            @Override
            public void onError(NetResult e) {
                RES_MBR_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MBR_0001, reqData);

        return RES_MBR_0001;
    }


}
