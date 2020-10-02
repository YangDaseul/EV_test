package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.VOC_1001;
import com.genesis.apps.comm.model.gra.api.VOC_1002;
import com.genesis.apps.comm.model.gra.api.VOC_1003;
import com.genesis.apps.comm.model.gra.api.VOC_1004;
import com.genesis.apps.comm.model.gra.api.VOC_1005;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class VOCRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<VOC_1001.Response>> RES_VOC_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<VOC_1002.Response>> RES_VOC_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<VOC_1003.Response>> RES_VOC_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<VOC_1004.Response>> RES_VOC_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<VOC_1005.Response>> RES_VOC_1005 = new MutableLiveData<>();
    
    
    @Inject
    public VOCRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<VOC_1001.Response>> REQ_VOC_1001(final VOC_1001.Request reqData) {
        RES_VOC_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_VOC_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, VOC_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_VOC_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_VOC_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_VOC_1001, reqData);

        return RES_VOC_1001;
    }

    public MutableLiveData<NetUIResponse<VOC_1002.Response>> REQ_VOC_1002(final VOC_1002.Request reqData) {
        RES_VOC_1002.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_VOC_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, VOC_1002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_VOC_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_VOC_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_VOC_1002, reqData);

        return RES_VOC_1002;
    }

    public MutableLiveData<NetUIResponse<VOC_1003.Response>> REQ_VOC_1003(final VOC_1003.Request reqData) {
        RES_VOC_1003.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_VOC_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, VOC_1003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_VOC_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_VOC_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_VOC_1003, reqData);

        return RES_VOC_1003;
    }


    public MutableLiveData<NetUIResponse<VOC_1004.Response>> REQ_VOC_1004(final VOC_1004.Request reqData) {
        RES_VOC_1004.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_VOC_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, VOC_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_VOC_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_VOC_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_VOC_1004, reqData);

        return RES_VOC_1004;
    }

    public MutableLiveData<NetUIResponse<VOC_1005.Response>> REQ_VOC_1005(final VOC_1005.Request reqData) {
        RES_VOC_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

                VOC_1005.Response response = new Gson().fromJson(object, VOC_1005.Response.class); //결과코드 및 결과메시지 저장
                response.setTermVO(new Gson().fromJson(object, TermVO.class)); //term데이터 저장
                RES_VOC_1005.setValue(NetUIResponse.success(response));
//                RES_VOC_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, VOC_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_VOC_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_VOC_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_VOC_1005, reqData);

        return RES_VOC_1005;
    }

}
