package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.REQ_1016;
import com.genesis.apps.comm.model.api.gra.REQ_1017;
import com.genesis.apps.comm.model.api.gra.REQ_1018;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1001;
import com.genesis.apps.comm.model.api.gra.REQ_1002;
import com.genesis.apps.comm.model.api.gra.REQ_1003;
import com.genesis.apps.comm.model.api.gra.REQ_1004;
import com.genesis.apps.comm.model.api.gra.REQ_1005;
import com.genesis.apps.comm.model.api.gra.REQ_1007;
import com.genesis.apps.comm.model.api.gra.REQ_1008;
import com.genesis.apps.comm.model.api.gra.REQ_1009;
import com.genesis.apps.comm.model.api.gra.REQ_1010;
import com.genesis.apps.comm.model.api.gra.REQ_1011;
import com.genesis.apps.comm.model.api.gra.REQ_1012;
import com.genesis.apps.comm.model.api.gra.REQ_1013;
import com.genesis.apps.comm.model.api.gra.REQ_1014;
import com.genesis.apps.comm.model.api.gra.REQ_1015;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class REQRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<REQ_1001.Response>> RES_REQ_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1002.Response>> RES_REQ_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1003.Response>> RES_REQ_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1004.Response>> RES_REQ_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1005.Response>> RES_REQ_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1007.Response>> RES_REQ_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1008.Response>> RES_REQ_1008 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1009.Response>> RES_REQ_1009 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1010.Response>> RES_REQ_1010 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1011.Response>> RES_REQ_1011 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1012.Response>> RES_REQ_1012 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1013.Response>> RES_REQ_1013 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1014.Response>> RES_REQ_1014 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1015.Response>> RES_REQ_1015 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1016.Response>> RES_REQ_1016 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1017.Response>> RES_REQ_1017 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<REQ_1018.Response>> RES_REQ_1018 = new MutableLiveData<>();


    @Inject
    public REQRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<REQ_1001.Response>> REQ_REQ_1001(final REQ_1001.Request reqData) {
        RES_REQ_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                                RES_REQ_1001.setValue(NetUIResponse.success(TestCode.REQ_1001));
                
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1001, reqData);

        return RES_REQ_1001;
    }


    public MutableLiveData<NetUIResponse<REQ_1002.Response>> REQ_REQ_1002(final REQ_1002.Request reqData) {
        RES_REQ_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1002.setValue(NetUIResponse.success(TestCode.REQ_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1002, reqData);

        return RES_REQ_1002;
    }


    public MutableLiveData<NetUIResponse<REQ_1003.Response>> REQ_REQ_1003(final REQ_1003.Request reqData) {
        RES_REQ_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1003.setValue(NetUIResponse.success(TestCode.REQ_1003));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1003, reqData);

        return RES_REQ_1003;
    }


    public MutableLiveData<NetUIResponse<REQ_1004.Response>> REQ_REQ_1004(final REQ_1004.Request reqData) {
        RES_REQ_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1004.setValue(NetUIResponse.success(TestCode.REQ_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1004, reqData);

        return RES_REQ_1004;
    }


    public MutableLiveData<NetUIResponse<REQ_1005.Response>> REQ_REQ_1005(final REQ_1005.Request reqData) {
        RES_REQ_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1005.Response.class)));
                
//                REQ_1005.Response response = new Gson().fromJson(object, REQ_1005.Response.class);
//                response.setREQStateVO(new Gson().fromJson(object, REQStateVO.class));
//                RES_REQ_1005.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1005.setValue(NetUIResponse.success(TestCode.REQ_1005));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1005, reqData);

        return RES_REQ_1005;
    }


    public MutableLiveData<NetUIResponse<REQ_1007.Response>> REQ_REQ_1007(final REQ_1007.Request reqData) {
        RES_REQ_1007.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1007.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1007.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1007.setValue(NetUIResponse.success(TestCode.REQ_1007));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1007, reqData);

        return RES_REQ_1007;
    }

    public MutableLiveData<NetUIResponse<REQ_1008.Response>> REQ_REQ_1008(final REQ_1008.Request reqData) {
        RES_REQ_1008.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1008.Response.class)));
//                                RES_REQ_1008.setValue(NetUIResponse.success(TestCode.REQ_1008));

            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1008.setValue(NetUIResponse.success(TestCode.REQ_1008));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1008, reqData);

        return RES_REQ_1008;
    }

    public MutableLiveData<NetUIResponse<REQ_1009.Response>> REQ_REQ_1009(final REQ_1009.Request reqData) {
        RES_REQ_1009.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1009.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1009.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1009.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1009.setValue(NetUIResponse.success(TestCode.REQ_1009));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1009.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1009, reqData);

        return RES_REQ_1009;
    }

    public MutableLiveData<NetUIResponse<REQ_1010.Response>> REQ_REQ_1010(final REQ_1010.Request reqData) {
        RES_REQ_1010.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1010.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1010.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1010.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1010.setValue(NetUIResponse.success(TestCode.REQ_1010));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1010.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1010, reqData);

        return RES_REQ_1010;
    }

    public MutableLiveData<NetUIResponse<REQ_1011.Response>> REQ_REQ_1011(final REQ_1011.Request reqData) {
        RES_REQ_1011.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1011.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1011.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1011.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1011.setValue(NetUIResponse.success(TestCode.REQ_1011));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1011.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1011, reqData);

        return RES_REQ_1011;
    }

    public MutableLiveData<NetUIResponse<REQ_1012.Response>> REQ_REQ_1012(final REQ_1012.Request reqData) {
        RES_REQ_1012.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1012.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1012.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1012.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1012.setValue(NetUIResponse.success(TestCode.REQ_1012));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1012.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1012, reqData);

        return RES_REQ_1012;
    }

    public MutableLiveData<NetUIResponse<REQ_1013.Response>> REQ_REQ_1013(final REQ_1013.Request reqData) {
        RES_REQ_1013.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
//                RES_REQ_1013.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1013.Response.class)));
                RES_REQ_1013.setValue(NetUIResponse.success(TestCode.REQ_1013));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1013.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1013.setValue(NetUIResponse.success(TestCode.REQ_1013));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1013.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1013, reqData);

        return RES_REQ_1013;
    }

    public MutableLiveData<NetUIResponse<REQ_1014.Response>> REQ_REQ_1014(final REQ_1014.Request reqData) {
        RES_REQ_1014.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
//                RES_REQ_1014.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1014.Response.class)));
                RES_REQ_1014.setValue(NetUIResponse.success(TestCode.REQ_1014));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1014.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1014.setValue(NetUIResponse.success(TestCode.REQ_1014));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1014.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1014, reqData);

        return RES_REQ_1014;
    }

    public MutableLiveData<NetUIResponse<REQ_1015.Response>> REQ_REQ_1015(final REQ_1015.Request reqData) {
        RES_REQ_1015.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
//                RES_REQ_1015.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1015.Response.class)));
                RES_REQ_1015.setValue(NetUIResponse.success(TestCode.REQ_1015));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1015.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1015.setValue(NetUIResponse.success(TestCode.REQ_1015));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1015.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1015, reqData);

        return RES_REQ_1015;
    }

    public MutableLiveData<NetUIResponse<REQ_1016.Response>> REQ_REQ_1016(final REQ_1016.Request reqData) {
        RES_REQ_1016.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1016.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1016.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1016.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1016.setValue(NetUIResponse.success(TestCode.REQ_1016));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1016.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1016, reqData);

        return RES_REQ_1016;
    }

    public MutableLiveData<NetUIResponse<REQ_1017.Response>> REQ_REQ_1017(final REQ_1017.Request reqData) {
        RES_REQ_1017.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1017.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1017.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1017.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1017.setValue(NetUIResponse.success(TestCode.REQ_1017));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1017.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1017, reqData);

        return RES_REQ_1017;
    }

    public MutableLiveData<NetUIResponse<REQ_1018.Response>> REQ_REQ_1018(final REQ_1018.Request reqData) {
        RES_REQ_1018.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REQ_1018.setValue(NetUIResponse.success(new Gson().fromJson(object, REQ_1018.Response.class)));
//                RES_REQ_1018.setValue(NetUIResponse.success(TestCode.REQ_1018));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REQ_1018.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_REQ_1018.setValue(NetUIResponse.success(TestCode.REQ_1018));
            }

            @Override
            public void onError(NetResult e) {
                RES_REQ_1018.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_REQ_1018, reqData);

        return RES_REQ_1018;
    }

}
