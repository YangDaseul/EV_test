package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.model.api.gra.RMT_1002;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.model.api.gra.RMT_1004;
import com.genesis.apps.comm.model.api.gra.RMT_1005;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class RMTRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<RMT_1001.Response>> RES_RMT_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<RMT_1002.Response>> RES_RMT_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<RMT_1003.Response>> RES_RMT_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<RMT_1004.Response>> RES_RMT_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<RMT_1005.Response>> RES_RMT_1005 = new MutableLiveData<>();

    @Inject
    public RMTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<RMT_1001.Response>> REQ_RMT_1001(final RMT_1001.Request reqData) {
        RES_RMT_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_RMT_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, RMT_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_RMT_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_RMT_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_RMT_1001, reqData);

        return RES_RMT_1001;
    }

    public MutableLiveData<NetUIResponse<RMT_1002.Response>> REQ_RMT_1002(final RMT_1002.Request reqData) {
        RES_RMT_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_RMT_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, RMT_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_RMT_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_RMT_1002.setValue(NetUIResponse.success(TestCode.RMT_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_RMT_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_RMT_1002, reqData);

        return RES_RMT_1002;
    }

    public MutableLiveData<NetUIResponse<RMT_1003.Response>> REQ_RMT_1003(final RMT_1003.Request reqData) {
        RES_RMT_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_RMT_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, RMT_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_RMT_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_RMT_1003.setValue(NetUIResponse.success(TestCode.RMT_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_RMT_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_RMT_1003, reqData);

        return RES_RMT_1003;
    }

    public MutableLiveData<NetUIResponse<RMT_1004.Response>> REQ_RMT_1004(final RMT_1004.Request reqData) {
        RES_RMT_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_RMT_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, RMT_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_RMT_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_RMT_1004.setValue(NetUIResponse.success(TestCode.RMT_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_RMT_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_RMT_1004, reqData);

        return RES_RMT_1004;
    }

    public MutableLiveData<NetUIResponse<RMT_1005.Response>> REQ_RMT_1005(final RMT_1005.Request reqData) {
        RES_RMT_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_RMT_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, RMT_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_RMT_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_RMT_1005.setValue(NetUIResponse.success(TestCode.RMT_1005));
            
            }

            @Override
            public void onError(NetResult e) {
                RES_RMT_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_RMT_1005, reqData);

        return RES_RMT_1005;
    }
}
