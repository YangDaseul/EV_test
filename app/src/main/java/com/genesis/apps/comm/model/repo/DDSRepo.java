package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.model.gra.api.DDS_1002;
import com.genesis.apps.comm.model.gra.api.DDS_1003;
import com.genesis.apps.comm.model.gra.api.DDS_1004;
import com.genesis.apps.comm.model.gra.api.DDS_1005;
import com.genesis.apps.comm.model.gra.api.DDS_1006;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DDSRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<DDS_1001.Response>> RES_DDS_1001 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<DDS_1002.Response>> RES_DDS_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DDS_1003.Response>> RES_DDS_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DDS_1004.Response>> RES_DDS_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DDS_1005.Response>> RES_DDS_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<DDS_1006.Response>> RES_DDS_1006 = new MutableLiveData<>();

    @Inject
    public DDSRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<DDS_1001.Response>> REQ_DDS_1001(final DDS_1001.Request reqData) {
        RES_DDS_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1001.setValue(NetUIResponse.success(TestCode.DDS_1001));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1001, reqData);

        return RES_DDS_1001;
    }

    public MutableLiveData<NetUIResponse<DDS_1002.Response>> REQ_DDS_1002(final DDS_1002.Request reqData) {
        RES_DDS_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1002.setValue(NetUIResponse.success(TestCode.DDS_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1002, reqData);

        return RES_DDS_1002;
    }

    public MutableLiveData<NetUIResponse<DDS_1003.Response>> REQ_DDS_1003(final DDS_1003.Request reqData) {
        RES_DDS_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1003.setValue(NetUIResponse.success(TestCode.DDS_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1003, reqData);

        return RES_DDS_1003;
    }

    public MutableLiveData<NetUIResponse<DDS_1004.Response>> REQ_DDS_1004(final DDS_1004.Request reqData) {
        RES_DDS_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1004.setValue(NetUIResponse.success(TestCode.DDS_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1004, reqData);

        return RES_DDS_1004;
    }

    public MutableLiveData<NetUIResponse<DDS_1005.Response>> REQ_DDS_1005(final DDS_1005.Request reqData) {
        RES_DDS_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1005.setValue(NetUIResponse.success(TestCode.DDS_1005));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1005, reqData);

        return RES_DDS_1005;
    }

    public MutableLiveData<NetUIResponse<DDS_1006.Response>> REQ_DDS_1006(final DDS_1006.Request reqData) {
        RES_DDS_1006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DDS_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, DDS_1006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_DDS_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_DDS_1006.setValue(NetUIResponse.success(TestCode.DDS_1006));
            }

            @Override
            public void onError(NetResult e) {
                RES_DDS_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_DDS_1006, reqData);

        return RES_DDS_1006;
    }


}
