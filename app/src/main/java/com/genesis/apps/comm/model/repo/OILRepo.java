package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.OIL_0001;
import com.genesis.apps.comm.model.api.gra.OIL_0002;
import com.genesis.apps.comm.model.api.gra.OIL_0003;
import com.genesis.apps.comm.model.api.gra.OIL_0004;
import com.genesis.apps.comm.model.api.gra.OIL_0005;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class OILRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<OIL_0001.Response>> RES_OIL_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<OIL_0002.Response>> RES_OIL_0002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<OIL_0003.Response>> RES_OIL_0003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<OIL_0004.Response>> RES_OIL_0004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<OIL_0005.Response>> RES_OIL_0005 = new MutableLiveData<>();

    @Inject
    public OILRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<OIL_0001.Response>> REQ_OIL_0001(final OIL_0001.Request reqData) {
        RES_OIL_0001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_OIL_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, OIL_0001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_OIL_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_OIL_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_OIL_0001, reqData);

        return RES_OIL_0001;
    }

    public MutableLiveData<NetUIResponse<OIL_0002.Response>> REQ_OIL_0002(final OIL_0002.Request reqData) {
        RES_OIL_0002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_OIL_0002.setValue(NetUIResponse.success(new Gson().fromJson(object, OIL_0002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_OIL_0002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_OIL_0002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_OIL_0002, reqData);

        return RES_OIL_0002;
    }

    public MutableLiveData<NetUIResponse<OIL_0003.Response>> REQ_OIL_0003(final OIL_0003.Request reqData) {
        RES_OIL_0003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_OIL_0003.setValue(NetUIResponse.success(new Gson().fromJson(object, OIL_0003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_OIL_0003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_OIL_0003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_OIL_0003, reqData);

        return RES_OIL_0003;
    }


    public MutableLiveData<NetUIResponse<OIL_0004.Response>> REQ_OIL_0004(final OIL_0004.Request reqData) {
        RES_OIL_0004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

                OIL_0004.Response response = new Gson().fromJson(object, OIL_0004.Response.class); //결과코드 및 결과메시지 저장
                response.setTermVO(new Gson().fromJson(object, TermVO.class)); //term데이터 저장
                RES_OIL_0004.setValue(NetUIResponse.success(response));
//                RES_OIL_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, OIL_0004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_OIL_0004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_OIL_0004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_OIL_0004, reqData);

        return RES_OIL_0004;
    }


    public MutableLiveData<NetUIResponse<OIL_0005.Response>> REQ_OIL_0005(final OIL_0005.Request reqData) {
        RES_OIL_0005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_OIL_0005.setValue(NetUIResponse.success(new Gson().fromJson(object, OIL_0005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_OIL_0005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_OIL_0005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_OIL_0005, reqData);

        return RES_OIL_0005;
    }

}
