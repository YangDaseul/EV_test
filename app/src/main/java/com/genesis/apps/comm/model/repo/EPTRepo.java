package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1001;
import com.genesis.apps.comm.model.api.gra.EPT_1002;
import com.genesis.apps.comm.model.api.gra.EPT_1003;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * @author Ki-man Kim
 */
public class EPTRepo {
    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<EPT_1001.Response>> RES_EPT_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<EPT_1002.Response>> RES_EPT_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<EPT_1003.Response>> RES_EPT_1003 = new MutableLiveData<>();

    @Inject
    public EPTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<EPT_1001.Response>> REQ_EPT_1001(final EPT_1001.Request reqData) {
        RES_EPT_1001.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_EPT_1001.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1001.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1001, reqData);
         */

        return RES_EPT_1001;
    }

    public MutableLiveData<NetUIResponse<EPT_1002.Response>> REQ_EPT_1002(final EPT_1002.Request reqData) {
        RES_EPT_1002.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_EPT_1002.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1002.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1002, reqData);
         */

        return RES_EPT_1002;
    }

    public MutableLiveData<NetUIResponse<EPT_1003.Response>> REQ_EPT_1003(final EPT_1003.Request reqData) {
        RES_EPT_1003.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_EPT_1003.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1003.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1003, reqData);
         */

        return RES_EPT_1003;
    }
} // end of class EPTRepo
