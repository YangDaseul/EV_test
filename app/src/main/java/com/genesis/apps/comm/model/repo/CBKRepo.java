package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.CBK_1001;
import com.genesis.apps.comm.model.gra.api.CBK_1002;
import com.genesis.apps.comm.model.gra.api.CBK_1005;
import com.genesis.apps.comm.model.gra.api.CBK_1006;
import com.genesis.apps.comm.model.gra.api.CBK_1007;
import com.genesis.apps.comm.model.gra.api.CBK_1008;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class CBKRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<CBK_1001.Response>> RES_CBK_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CBK_1002.Response>> RES_CBK_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CBK_1005.Response>> RES_CBK_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CBK_1006.Response>> RES_CBK_1006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CBK_1007.Response>> RES_CBK_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CBK_1008.Response>> RES_CBK_1008 = new MutableLiveData<>();

    @Inject
    public CBKRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<CBK_1001.Response>> REQ_CBK_1001(final CBK_1001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_CBK_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1001, reqData);

        return RES_CBK_1001;
    }
    

    public MutableLiveData<NetUIResponse<CBK_1002.Response>> REQ_CBK_1002(final CBK_1002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CBK_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CBK_1002.setValue(NetUIResponse.success(TestCode.CBK_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1002, reqData);

        return RES_CBK_1002;
    }


    public MutableLiveData<NetUIResponse<CBK_1005.Response>> REQ_CBK_1005(final CBK_1005.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1005.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CBK_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CBK_1005.setValue(NetUIResponse.success(TestCode.CBK_1005));
                
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1005, reqData);

        return RES_CBK_1005;
    }


    public MutableLiveData<NetUIResponse<CBK_1006.Response>> REQ_CBK_1006(final CBK_1006.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1006.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CBK_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CBK_1006.setValue(NetUIResponse.success(TestCode.CBK_1006));
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1006, reqData);

        return RES_CBK_1006;
    }


    public MutableLiveData<NetUIResponse<CBK_1007.Response>> REQ_CBK_1007(final CBK_1007.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1007.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1007.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CBK_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CBK_1007.setValue(NetUIResponse.success(TestCode.CBK_1007));
                
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1007, reqData);

        return RES_CBK_1007;
    }

    public MutableLiveData<NetUIResponse<CBK_1008.Response>> REQ_CBK_1008(final CBK_1008.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CBK_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, CBK_1008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CBK_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CBK_1008.setValue(NetUIResponse.success(TestCode.CBK_1008));
            }

            @Override
            public void onError(NetResult e) {
                RES_CBK_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CBK_1008, reqData);

        return RES_CBK_1008;
    }

}
