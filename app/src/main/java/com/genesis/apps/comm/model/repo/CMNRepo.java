package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.CMN_0001;
import com.genesis.apps.comm.model.api.gra.CMN_0002;
import com.genesis.apps.comm.model.api.gra.CMN_0003;
import com.genesis.apps.comm.model.api.gra.CMN_0004;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class CMNRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<CMN_0001.Response>> RES_CMN_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CMN_0002.Response>> RES_CMN_0002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CMN_0003.Response>> RES_CMN_0003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CMN_0004.Response>> RES_CMN_0004 = new MutableLiveData<>();

    @Inject
    public CMNRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<CMN_0001.Response>> REQ_CMN_0001(final CMN_0001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CMN_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, CMN_0001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CMN_0001.setValue(NetUIResponse.success(TestCode.CMN_0001));
//                RES_CMN_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CMN_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CMN_0001, reqData);

        return RES_CMN_0001;
    }

    public MutableLiveData<NetUIResponse<CMN_0002.Response>> REQ_CMN_0002(final CMN_0002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CMN_0002.setValue(NetUIResponse.success(new Gson().fromJson(object, CMN_0002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CMN_0002.setValue(NetUIResponse.success(TestCode.CMN_0002));
//              RES_CMN_0002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CMN_0002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CMN_0002, reqData);

        return RES_CMN_0002;
    }

    public MutableLiveData<NetUIResponse<CMN_0003.Response>> REQ_CMN_0003(final CMN_0003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CMN_0003.setValue(NetUIResponse.success(new Gson().fromJson(object, CMN_0003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CMN_0003.setValue(NetUIResponse.success(TestCode.CMN_0003));
//                RES_CMN_0003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CMN_0003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CMN_0003, reqData);

        return RES_CMN_0003;
    }


    public MutableLiveData<NetUIResponse<CMN_0004.Response>> REQ_CMN_0004(final CMN_0004.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                CMN_0004.Response response = new Gson().fromJson(object, CMN_0004.Response.class); //결과코드 및 결과메시지 저장
                response.setTermVO(new Gson().fromJson(object, TermVO.class)); //term데이터 저장
                RES_CMN_0004.setValue(NetUIResponse.success(response));
//                RES_CMN_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, CMN_0004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CMN_0004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CMN_0004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CMN_0004, reqData);

        return RES_CMN_0004;
    }


}
