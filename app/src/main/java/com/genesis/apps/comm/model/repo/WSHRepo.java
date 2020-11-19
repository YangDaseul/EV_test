package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.WSH_1001;
import com.genesis.apps.comm.model.api.gra.WSH_1002;
import com.genesis.apps.comm.model.api.gra.WSH_1003;
import com.genesis.apps.comm.model.api.gra.WSH_1004;
import com.genesis.apps.comm.model.api.gra.WSH_1005;
import com.genesis.apps.comm.model.api.gra.WSH_1006;
import com.genesis.apps.comm.model.api.gra.WSH_1007;
import com.genesis.apps.comm.model.api.gra.WSH_1008;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

//CarWashHistory : 세차 예약 내역
public class WSHRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<WSH_1001.Response>> RES_WSH_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1002.Response>> RES_WSH_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1003.Response>> RES_WSH_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1004.Response>> RES_WSH_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1005.Response>> RES_WSH_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1006.Response>> RES_WSH_1006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1007.Response>> RES_WSH_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<WSH_1008.Response>> RES_WSH_1008 = new MutableLiveData<>();


    @Inject
    public WSHRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    //service + 소낙스 세차이용권 조회
    public MutableLiveData<NetUIResponse<WSH_1001.Response>> REQ_WSH_1001(final WSH_1001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1001.setValue(NetUIResponse.success(TestCode.WSH_1001));
                RES_WSH_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1001, reqData);

        return RES_WSH_1001;
    }

    //service + 소낙스 세차이용권 선택 (고객의 근접한 소낙스 지점 찾기)
    public MutableLiveData<NetUIResponse<WSH_1002.Response>> REQ_WSH_1002(final WSH_1002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1002.setValue(NetUIResponse.success(TestCode.WSH_1002));
                RES_WSH_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1002, reqData);

        return RES_WSH_1002;
    }

    //    service + 소낙스 세차예약
    public MutableLiveData<NetUIResponse<WSH_1003.Response>> REQ_WSH_1003(final WSH_1003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1003.setValue(NetUIResponse.success(TestCode.WSH_1003));
                RES_WSH_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1003, reqData);

        return RES_WSH_1003;
    }

    //service + 소낙스 세차예약 내역
    public MutableLiveData<NetUIResponse<WSH_1004.Response>> REQ_WSH_1004(final WSH_1004.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1004.setValue(NetUIResponse.success(TestCode.WSH_1004));
                RES_WSH_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1004, reqData);

        return RES_WSH_1004;
    }

    //service + 소낙스 직원에게 확인받기
    public MutableLiveData<NetUIResponse<WSH_1005.Response>> REQ_WSH_1005(final WSH_1005.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1005.setValue(NetUIResponse.success(TestCode.WSH_1005));
                RES_WSH_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1005, reqData);

        return RES_WSH_1005;
    }

    //service + 소낙스 예약취소
    public MutableLiveData<NetUIResponse<WSH_1006.Response>> REQ_WSH_1006(final WSH_1006.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1006.setValue(NetUIResponse.success(TestCode.WSH_1006));
                RES_WSH_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1006, reqData);

        return RES_WSH_1006;
    }

    //service + 소낙스 평가지 요청
    public MutableLiveData<NetUIResponse<WSH_1007.Response>> REQ_WSH_1007(final WSH_1007.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1007.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1007.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1007.setValue(NetUIResponse.success(TestCode.WSH_1007));
                RES_WSH_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1007, reqData);

        return RES_WSH_1007;
    }

    //service + 소낙스 평가 요청
    public MutableLiveData<NetUIResponse<WSH_1008.Response>> REQ_WSH_1008(final WSH_1008.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WSH_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, WSH_1008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_WSH_1008.setValue(NetUIResponse.success(TestCode.WSH_1008));
                RES_WSH_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WSH_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_WSH_1008, reqData);

        return RES_WSH_1008;
    }
}
