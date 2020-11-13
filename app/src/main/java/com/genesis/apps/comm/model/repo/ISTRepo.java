package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.IST_1001;
import com.genesis.apps.comm.model.api.gra.IST_1002;
import com.genesis.apps.comm.model.api.gra.IST_1003;
import com.genesis.apps.comm.model.api.gra.IST_1004;
import com.genesis.apps.comm.model.api.gra.IST_1005;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class ISTRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<IST_1001.Response>> RES_IST_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<IST_1002.Response>> RES_IST_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<IST_1003.Response>> RES_IST_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<IST_1004.Response>> RES_IST_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<IST_1005.Response>> RES_IST_1005 = new MutableLiveData<>();

    @Inject
    public ISTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<IST_1001.Response>> REQ_IST_1001(final IST_1001.Request reqData) {
        RES_IST_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_IST_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, IST_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_IST_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_IST_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_IST_1001, reqData);

        return RES_IST_1001;
    }

    public MutableLiveData<NetUIResponse<IST_1002.Response>> REQ_IST_1002(final IST_1002.Request reqData) {
        RES_IST_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_IST_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, IST_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_IST_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_IST_1002.setValue(NetUIResponse.success(TestCode.IST_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_IST_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_IST_1002, reqData);

        return RES_IST_1002;
    }

    public MutableLiveData<NetUIResponse<IST_1003.Response>> REQ_IST_1003(final IST_1003.Request reqData) {
        RES_IST_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_IST_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, IST_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_IST_1003.setValue(NetUIResponse.error(e.getMseeage(), null));

                RES_IST_1003.setValue(NetUIResponse.success(TestCode.IST_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_IST_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_IST_1003, reqData);

        return RES_IST_1003;
    }

    public MutableLiveData<NetUIResponse<IST_1004.Response>> REQ_IST_1004(final IST_1004.Request reqData) {
        RES_IST_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_IST_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, IST_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_IST_1004.setValue(NetUIResponse.error(e.getMseeage(), null));

                RES_IST_1004.setValue(NetUIResponse.success(TestCode.IST_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_IST_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_IST_1004, reqData);

        return RES_IST_1004;
    }

    public MutableLiveData<NetUIResponse<IST_1005.Response>> REQ_IST_1005(final IST_1005.Request reqData) {
        RES_IST_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_IST_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, IST_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_IST_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_IST_1005.setValue(NetUIResponse.success(TestCode.IST_1005));
            
            }

            @Override
            public void onError(NetResult e) {
                RES_IST_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_IST_1005, reqData);

        return RES_IST_1005;
    }



}
