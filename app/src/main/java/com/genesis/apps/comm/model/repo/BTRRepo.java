package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.BTR_1001;
import com.genesis.apps.comm.model.gra.api.BTR_1008;
import com.genesis.apps.comm.model.gra.api.BTR_1009;
import com.genesis.apps.comm.model.gra.api.BTR_1010;
import com.genesis.apps.comm.model.gra.api.BTR_2001;
import com.genesis.apps.comm.model.gra.api.BTR_2002;
import com.genesis.apps.comm.model.gra.api.BTR_2003;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class BTRRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<BTR_1001.Response>> RES_BTR_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_1008.Response>> RES_BTR_1008 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_1009.Response>> RES_BTR_1009 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_1010.Response>> RES_BTR_1010 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_2001.Response>> RES_BTR_2001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_2002.Response>> RES_BTR_2002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<BTR_2003.Response>> RES_BTR_2003 = new MutableLiveData<>();
    
    @Inject
    public BTRRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<BTR_1001.Response>> REQ_BTR_1001(final BTR_1001.Request reqData) {
        RES_BTR_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

                BTR_1001.Response response = new Gson().fromJson(object, BTR_1001.Response.class); //결과코드 및 결과메시지 저장
                response.setBtrVO(new Gson().fromJson(object, BtrVO.class)); //btr 데이터 저장
                RES_BTR_1001.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_1001, reqData);

        return RES_BTR_1001;
    }




    public MutableLiveData<NetUIResponse<BTR_1008.Response>> REQ_BTR_1008(final BTR_1008.Request reqData) {
        RES_BTR_1008.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BTR_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, BTR_1008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_1008, reqData);

        return RES_BTR_1008;
    }


    public MutableLiveData<NetUIResponse<BTR_1009.Response>> REQ_BTR_1009(final BTR_1009.Request reqData) {
        RES_BTR_1009.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

                BTR_1009.Response response = new Gson().fromJson(object, BTR_1009.Response.class); //결과코드 및 결과메시지 저장
                response.setBtrVO(new Gson().fromJson(object, BtrVO.class)); //btr 데이터 저장
                RES_BTR_1009.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_1009.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_1009.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_1009, reqData);

        return RES_BTR_1009;
    }


    public MutableLiveData<NetUIResponse<BTR_1010.Response>> REQ_BTR_1010(final BTR_1010.Request reqData) {
        RES_BTR_1010.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BTR_1010.setValue(NetUIResponse.success(new Gson().fromJson(object, BTR_1010.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_1010.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_1010.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_1010, reqData);

        return RES_BTR_1010;
    }

    public MutableLiveData<NetUIResponse<BTR_2001.Response>> REQ_BTR_2001(final BTR_2001.Request reqData) {
        RES_BTR_2001.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BTR_2001.setValue(NetUIResponse.success(new Gson().fromJson(object, BTR_2001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_2001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_2001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_2001, reqData);

        return RES_BTR_2001;
    }

    public MutableLiveData<NetUIResponse<BTR_2002.Response>> REQ_BTR_2002(final BTR_2002.Request reqData) {
        RES_BTR_2002.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BTR_2002.setValue(NetUIResponse.success(new Gson().fromJson(object, BTR_2002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_2002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_2002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_2002, reqData);

        return RES_BTR_2002;
    }

    public MutableLiveData<NetUIResponse<BTR_2003.Response>> REQ_BTR_2003(final BTR_2003.Request reqData) {
        RES_BTR_2003.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_BTR_2003.setValue(NetUIResponse.success(new Gson().fromJson(object, BTR_2003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_BTR_2003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_BTR_2003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_BTR_2003, reqData);

        return RES_BTR_2003;
    }

}
