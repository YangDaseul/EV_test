package com.genesis.apps.comm.model.gra.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BTR_1001;
import com.genesis.apps.comm.model.gra.BTR_1008;
import com.genesis.apps.comm.model.gra.BTR_1009;
import com.genesis.apps.comm.model.gra.BTR_1010;
import com.genesis.apps.comm.model.gra.VOC_1005;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.TermVO;
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

}
