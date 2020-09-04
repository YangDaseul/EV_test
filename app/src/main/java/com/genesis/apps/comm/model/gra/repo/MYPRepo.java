package com.genesis.apps.comm.model.gra.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.MYP_0004;
import com.genesis.apps.comm.model.gra.MYP_0005;
import com.genesis.apps.comm.model.gra.MYP_1003;
import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8002;
import com.genesis.apps.comm.model.gra.MYP_8003;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class MYPRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<MYP_0001.Response>> RES_MYP_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_0004.Response>> RES_MYP_0004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_0005.Response>> RES_MYP_0005 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<MYP_1003.Response>> RES_MYP_1003 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_8002.Response>> RES_MYP_8002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_8003.Response>> RES_MYP_8003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004 = new MutableLiveData<>();

    @Inject
    public MYPRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<MYP_0001.Response>> REQ_MYP_0001(final MYP_0001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0001, reqData);

        return RES_MYP_0001;
    }

    public MutableLiveData<NetUIResponse<MYP_0004.Response>> REQ_MYP_0004(final MYP_0004.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0004.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0004, reqData);

        return RES_MYP_0004;
    }


    public MutableLiveData<NetUIResponse<MYP_0005.Response>> REQ_MYP_0005(final MYP_0005.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0005.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0005, reqData);

        return RES_MYP_0005;
    }

    public MutableLiveData<NetUIResponse<MYP_1003.Response>> REQ_MYP_1003(final MYP_1003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_1003, reqData);

        return RES_MYP_1003;
    }

    public MutableLiveData<NetUIResponse<MYP_8001.Response>> REQ_MYP_8001(final MYP_8001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8001, reqData);

        return RES_MYP_8001;
    }

    public MutableLiveData<NetUIResponse<MYP_8002.Response>> REQ_MYP_8002(final MYP_8002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8002.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8002, reqData);

        return RES_MYP_8002;
    }

    public MutableLiveData<NetUIResponse<MYP_8003.Response>> REQ_MYP_8003(final MYP_8003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8003, reqData);

        return RES_MYP_8003;
    }

    public MutableLiveData<NetUIResponse<MYP_8004.Response>> REQ_MYP_8004(final MYP_8004.Request reqData) {
        RES_MYP_8004.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8004.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8004, reqData);

        return RES_MYP_8004;
    }

}
