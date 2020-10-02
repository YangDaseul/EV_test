package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.PUB_1002;
import com.genesis.apps.comm.model.gra.api.PUB_1003;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class PUBRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<PUB_1002.Response>> RES_PUB_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<PUB_1003.Response>> RES_PUB_1003 = new MutableLiveData<>();

    @Inject
    public PUBRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<PUB_1002.Response>> REQ_PUB_1002(final PUB_1002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_PUB_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, PUB_1002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_PUB_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_PUB_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_PUB_1002, reqData);

        return RES_PUB_1002;
    }

    public MutableLiveData<NetUIResponse<PUB_1003.Response>> REQ_PUB_1003(final PUB_1003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_PUB_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, PUB_1003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_PUB_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_PUB_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_PUB_1003, reqData);

        return RES_PUB_1003;
    }
}
