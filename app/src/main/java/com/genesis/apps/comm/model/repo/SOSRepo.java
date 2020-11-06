package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.OIL_0004;
import com.genesis.apps.comm.model.gra.api.SOS_1001;
import com.genesis.apps.comm.model.gra.api.SOS_1002;
import com.genesis.apps.comm.model.gra.api.SOS_1003;
import com.genesis.apps.comm.model.gra.api.SOS_1004;
import com.genesis.apps.comm.model.gra.api.SOS_1005;
import com.genesis.apps.comm.model.gra.api.SOS_1006;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class SOSRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<SOS_1001.Response>> RES_SOS_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_1002.Response>> RES_SOS_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_1003.Response>> RES_SOS_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_1004.Response>> RES_SOS_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_1005.Response>> RES_SOS_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_1006.Response>> RES_SOS_1006 = new MutableLiveData<>();

    @Inject
    public SOSRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<SOS_1001.Response>> REQ_SOS_1001(final SOS_1001.Request reqData) {
        RES_SOS_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_SOS_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_SOS_1001.setValue(NetUIResponse.success(TestCode.SOS_1001));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1001, reqData);

        return RES_SOS_1001;
    }


    public MutableLiveData<NetUIResponse<SOS_1002.Response>> REQ_SOS_1002(final SOS_1002.Request reqData) {
        RES_SOS_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_SOS_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_SOS_1002.setValue(NetUIResponse.success(TestCode.SOS_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1002, reqData);

        return RES_SOS_1002;
    }


    public MutableLiveData<NetUIResponse<SOS_1003.Response>> REQ_SOS_1003(final SOS_1003.Request reqData) {
        RES_SOS_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_SOS_1003.setValue(NetUIResponse.success(TestCode.SOS_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1003, reqData);

        return RES_SOS_1003;
    }


    public MutableLiveData<NetUIResponse<SOS_1004.Response>> REQ_SOS_1004(final SOS_1004.Request reqData) {
        RES_SOS_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_SOS_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_SOS_1004.setValue(NetUIResponse.success(TestCode.SOS_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1004, reqData);

        return RES_SOS_1004;
    }


    public MutableLiveData<NetUIResponse<SOS_1005.Response>> REQ_SOS_1005(final SOS_1005.Request reqData) {
        RES_SOS_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                SOS_1005.Response response = new Gson().fromJson(object, SOS_1005.Response.class);
                response.setSosStateVO(new Gson().fromJson(object, SOSStateVO.class));
                RES_SOS_1005.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_SOS_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
                String json = TestCode.SOS_1005;
                SOS_1005.Response response = new Gson().fromJson(json, SOS_1005.Response.class);
                response.setSosStateVO(new Gson().fromJson(json, SOSStateVO.class));
                RES_SOS_1005.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1005, reqData);

        return RES_SOS_1005;
    }


    public MutableLiveData<NetUIResponse<SOS_1006.Response>> REQ_SOS_1006(final SOS_1006.Request reqData) {
        RES_SOS_1006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
//                RES_SOS_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1006.Response.class)));
                SOS_1006.Response response = new Gson().fromJson(object, SOS_1006.Response.class);
                response.setSosDriverVO(new Gson().fromJson(object, SOSDriverVO.class));
                RES_SOS_1006.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_SOS_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
                String json = TestCode.SOS_1006;
                SOS_1006.Response response = new Gson().fromJson(json, SOS_1006.Response.class);
                response.setSosDriverVO(new Gson().fromJson(json, SOSDriverVO.class));
                RES_SOS_1006.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1006, reqData);

        return RES_SOS_1006;
    }

}
