package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.api.gra.STC_1002;
import com.genesis.apps.comm.model.api.gra.STC_1003;
import com.genesis.apps.comm.model.api.gra.STC_1004;
import com.genesis.apps.comm.model.api.gra.STC_1005;
import com.genesis.apps.comm.model.api.gra.STC_1006;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * @author Ki-man Kim
 */
public class STCRepo {
    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<STC_1001.Response>> RES_STC_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STC_1002.Response>> RES_STC_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STC_1003.Response>> RES_STC_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STC_1004.Response>> RES_STC_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STC_1005.Response>> RES_STC_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<STC_1006.Response>> RES_STC_1006 = new MutableLiveData<>();

    @Inject
    public STCRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<STC_1001.Response>> REQ_STC_1001(final STC_1001.Request reqData) {
        RES_STC_1001.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1001.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1001.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1001, reqData);
         */

        return RES_STC_1001;
    }

    public MutableLiveData<NetUIResponse<STC_1002.Response>> REQ_STC_1002(final STC_1002.Request reqData) {
        RES_STC_1002.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1002.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1002.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1002, reqData);
         */

        return RES_STC_1002;
    }

    public MutableLiveData<NetUIResponse<STC_1003.Response>> REQ_STC_1003(final STC_1003.Request reqData) {
        RES_STC_1003.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1003.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1003.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1003, reqData);
         */

        return RES_STC_1003;
    }

    public MutableLiveData<NetUIResponse<STC_1004.Response>> REQ_STC_1004(final STC_1004.Request reqData) {
        RES_STC_1004.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1004.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1004.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1004, reqData);
         */

        return RES_STC_1004;
    }

    public MutableLiveData<NetUIResponse<STC_1005.Response>> REQ_STC_1005(final STC_1005.Request reqData) {
        RES_STC_1005.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1005.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1005.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1005, reqData);
         */

        return RES_STC_1005;
    }

    public MutableLiveData<NetUIResponse<STC_1006.Response>> REQ_STC_1006(final STC_1006.Request reqData) {
        RES_STC_1006.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "";
        RES_STC_1006.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1006.Response.class)));
        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_1006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_1006, reqData);
         */

        return RES_STC_1006;
    }
} // end of class STCRepo
