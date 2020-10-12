package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1001;
import com.genesis.apps.comm.model.gra.api.GNS_1002;
import com.genesis.apps.comm.model.gra.api.GNS_1003;
import com.genesis.apps.comm.model.gra.api.GNS_1004;
import com.genesis.apps.comm.model.gra.api.GNS_1005;
import com.genesis.apps.comm.model.gra.api.GNS_1006;
import com.genesis.apps.comm.model.gra.api.GNS_1007;
import com.genesis.apps.comm.model.gra.api.GNS_1008;
import com.genesis.apps.comm.model.gra.api.GNS_1009;
import com.genesis.apps.comm.model.gra.api.GNS_1010;
import com.genesis.apps.comm.model.gra.api.GNS_1011;
import com.genesis.apps.comm.model.gra.api.GNS_1012;
import com.genesis.apps.comm.model.gra.api.GNS_1013;
import com.genesis.apps.comm.model.gra.api.GNS_1014;
import com.genesis.apps.comm.model.gra.api.GNS_1015;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class GNSRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<GNS_1001.Response>> RES_GNS_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1002.Response>> RES_GNS_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1003.Response>> RES_GNS_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1004.Response>> RES_GNS_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1005.Response>> RES_GNS_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1006.Response>> RES_GNS_1006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1007.Response>> RES_GNS_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1008.Response>> RES_GNS_1008 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1009.Response>> RES_GNS_1009 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1010.Response>> RES_GNS_1010 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1011.Response>> RES_GNS_1011 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1012.Response>> RES_GNS_1012 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<GNS_1013.Response>> RES_GNS_1013 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1014.Response>> RES_GNS_1014 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GNS_1015.Response>> RES_GNS_1015 = new MutableLiveData<>();

    @Inject
    public GNSRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<GNS_1001.Response>> REQ_GNS_1001(final GNS_1001.Request reqData) {
        RES_GNS_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1001.setValue(NetUIResponse.success(TestCode.GNS_1001));
//                RES_GNS_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1001, reqData);

        return RES_GNS_1001;
    }

    public MutableLiveData<NetUIResponse<GNS_1002.Response>> REQ_GNS_1002(final GNS_1002.Request reqData) {
        RES_GNS_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_GNS_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_GNS_1002.setValue(NetUIResponse.success(TestCode.GNS_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1002, reqData);

        return RES_GNS_1002;
    }

    public MutableLiveData<NetUIResponse<GNS_1003.Response>> REQ_GNS_1003(final GNS_1003.Request reqData) {
        RES_GNS_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_GNS_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_GNS_1003.setValue(NetUIResponse.success(TestCode.GNS_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1003, reqData);

        return RES_GNS_1003;
    }

    public MutableLiveData<NetUIResponse<GNS_1004.Response>> REQ_GNS_1004(final GNS_1004.Request reqData) {
        RES_GNS_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_GNS_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_GNS_1004.setValue(NetUIResponse.success(TestCode.GNS_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1004, reqData);

        return RES_GNS_1004;
    }

    public MutableLiveData<NetUIResponse<GNS_1005.Response>> REQ_GNS_1005(final GNS_1005.Request reqData) {
        RES_GNS_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_GNS_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_GNS_1005.setValue(NetUIResponse.success(TestCode.GNS_1005));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1005, reqData);

        return RES_GNS_1005;
    }

    public MutableLiveData<NetUIResponse<GNS_1006.Response>> REQ_GNS_1006(final GNS_1006.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1006, reqData);

        return RES_GNS_1006;
    }

    public MutableLiveData<NetUIResponse<GNS_1007.Response>> REQ_GNS_1007(final GNS_1007.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1007.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1007.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1007, reqData);

        return RES_GNS_1007;
    }

    public MutableLiveData<NetUIResponse<GNS_1008.Response>> REQ_GNS_1008(final GNS_1008.Request reqData) {

        netCaller.sendFileToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1008, reqData,reqData.getFile(),reqData.getImageKeyName());

        return RES_GNS_1008;
    }

    public MutableLiveData<NetUIResponse<GNS_1009.Response>> REQ_GNS_1009(final GNS_1009.Request reqData) {

        netCaller.sendFileToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1009.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1009.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1009.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1009.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1009, reqData,reqData.getFile(),reqData.getImageKeyName());

        return RES_GNS_1009;
    }

    public MutableLiveData<NetUIResponse<GNS_1010.Response>> REQ_GNS_1010(final GNS_1010.Request reqData) {

        RES_GNS_1010.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1010.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1010.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_GNS_1010.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_GNS_1010.setValue(NetUIResponse.success(TestCode.GNS_1010));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1010.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1010, reqData);

        return RES_GNS_1010;
    }

    public MutableLiveData<NetUIResponse<GNS_1011.Response>> REQ_GNS_1011(final GNS_1011.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1011.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1011.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1011.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1011.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1011, reqData);

        return RES_GNS_1011;
    }

    public MutableLiveData<NetUIResponse<GNS_1012.Response>> REQ_GNS_1012(final GNS_1012.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1012.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1012.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1012.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1012.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1012, reqData);

        return RES_GNS_1012;
    }



    public MutableLiveData<NetUIResponse<GNS_1013.Response>> REQ_GNS_1013(final GNS_1013.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1013.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1013.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1013.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1013.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1013, reqData);

        return RES_GNS_1013;
    }
    public MutableLiveData<NetUIResponse<GNS_1014.Response>> REQ_GNS_1014(final GNS_1014.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1014.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1014.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1014.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1014.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1014, reqData);

        return RES_GNS_1014;
    }
    public MutableLiveData<NetUIResponse<GNS_1015.Response>> REQ_GNS_1015(final GNS_1015.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GNS_1015.setValue(NetUIResponse.success(new Gson().fromJson(object, GNS_1015.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GNS_1015.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GNS_1015.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_GNS_1015, reqData);

        return RES_GNS_1015;
    }
    
}
