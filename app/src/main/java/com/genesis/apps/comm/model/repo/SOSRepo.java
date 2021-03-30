package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1002;
import com.genesis.apps.comm.model.api.gra.SOS_1003;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.api.gra.SOS_1005;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.api.gra.SOS_3001;
import com.genesis.apps.comm.model.api.gra.SOS_3002;
import com.genesis.apps.comm.model.api.gra.SOS_3003;
import com.genesis.apps.comm.model.api.gra.SOS_3004;
import com.genesis.apps.comm.model.api.gra.SOS_3005;
import com.genesis.apps.comm.model.api.gra.SOS_3006;
import com.genesis.apps.comm.model.api.gra.SOS_3007;
import com.genesis.apps.comm.model.api.gra.SOS_3008;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.SOSStateVO;
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

    public final MutableLiveData<NetUIResponse<SOS_3001.Response>> RES_SOS_3001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3002.Response>> RES_SOS_3002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3003.Response>> RES_SOS_3003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3004.Response>> RES_SOS_3004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3005.Response>> RES_SOS_3005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3006.Response>> RES_SOS_3006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3007.Response>> RES_SOS_3007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<SOS_3008.Response>> RES_SOS_3008 = new MutableLiveData<>();



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

//                RES_SOS_1001.setValue(NetUIResponse.success(new Gson().fromJson("{\n" +
//                        "  \"rtCd\": \"0000\",\n" +
//                        "  \"rtMsg\": \"Success\",\n" +
//                        "  \"subspYn\": \"Y\",\n" +
//                        "  \"tmpAcptNo\": \"28843\",\n" +
//                        "  \"pgrsStusCd\": \"S\"\n" +
//                        "}", SOS_1001.Response.class)));

            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_SOS_1001.setValue(NetUIResponse.success(TestCode.SOS_1001));
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
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_SOS_1002.setValue(NetUIResponse.success(TestCode.SOS_1002));
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
                RES_SOS_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_SOS_1004.setValue(NetUIResponse.success(TestCode.SOS_1004));
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
                RES_SOS_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
//                String json = TestCode.SOS_1005;
//                SOS_1005.Response response = new Gson().fromJson(json, SOS_1005.Response.class);
//                response.setSosStateVO(new Gson().fromJson(json, SOSStateVO.class));
//                RES_SOS_1005.setValue(NetUIResponse.success(response));
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

//                object = "{\n" +
//                        "  \"rtCd\": \"0000\",\n" +
//                        "  \"rtMsg\": \"Success\",\n" +
//                        "  \"tmpAcptNo\": \"28843\",\n" +
//                        "  \"tmpAcptDtm\": \"20200827112847\",\n" +
//                        "  \"areaClsCd\": \"R\",\n" +
//                        "  \"addr\": \"서울시 용산구 원효\",\n" +
//                        "  \"carRegNo\": \"23가9876\",\n" +
//                        "  \"memo\": \"메모내용\",\n" +
//                        "  \"controlTel\": \"01011111111\",\n" +
//                        "  \"carNo\": \"10라0142\",\n" +
//                        "  \"carTypeNm\": \"아이오닉EV\",\n" +
//                        "  \"receiveDtm\": \"20180128105929\",\n" +
//                        "  \"gCustY\": \"37.53235333090944\",\n" +
//                        "  \"startX\": \"45728206\",\n" +
//                        "  \"startY\": \"13502901\",\n" +
//                        "  \"finishX\": \"45703607\",\n" +
//                        "  \"finishY\": \"13510640\",\n" +
//                        "  \"gCustX\": \"126.95236305538208\",\n" +
//                        "  \"gYpos\": \"37.510865\",\n" +
//                        "  \"gXpos\": \"127.020693\",\n" +
//                        "  \"sallocNm\": \"출동(하이카)\"\n" +
//                        "}";

//                RES_SOS_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_1006.Response.class)));
                SOS_1006.Response response = new Gson().fromJson(object, SOS_1006.Response.class);
                response.setSosDriverVO(new Gson().fromJson(object, SOSDriverVO.class));
                RES_SOS_1006.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
//                String json = TestCode.SOS_1006;
//                SOS_1006.Response response = new Gson().fromJson(json, SOS_1006.Response.class);
//                response.setSosDriverVO(new Gson().fromJson(json, SOSDriverVO.class));
//                RES_SOS_1006.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_1006, reqData);

        return RES_SOS_1006;
    }

    public MutableLiveData<NetUIResponse<SOS_3001.Response>> REQ_SOS_3001(final SOS_3001.Request reqData) {
        RES_SOS_3001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3001.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3001.Response.class)));

//                RES_SOS_3001.setValue(NetUIResponse.success(new Gson().fromJson("{\n" +
//                        "  \"rtCd\": \"0000\",\n" +
//                        "  \"rtMsg\": \"Success\",\n" +
//                        "  \"subspYn\": \"Y\",\n" +
//                        "  \"tmpAcptNo\": \"28843\",\n" +
//                        "  \"pgrsStusCd\": \"S\"\n" +
//                        "}", SOS_3001.Response.class)));

            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_SOS_3001.setValue(NetUIResponse.success(TestCode.SOS_3001));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3001, reqData);

        return RES_SOS_3001;
    }

    public MutableLiveData<NetUIResponse<SOS_3002.Response>> REQ_SOS_3002(final SOS_3002.Request reqData) {
        RES_SOS_3002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3002.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3002, reqData);

        return RES_SOS_3002;
    }

    public MutableLiveData<NetUIResponse<SOS_3003.Response>> REQ_SOS_3003(final SOS_3003.Request reqData) {
        RES_SOS_3003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3003.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3003, reqData);

        return RES_SOS_3003;
    }

    public MutableLiveData<NetUIResponse<SOS_3004.Response>> REQ_SOS_3004(final SOS_3004.Request reqData) {
        RES_SOS_3004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3004.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3004, reqData);

        return RES_SOS_3004;
    }

    public MutableLiveData<NetUIResponse<SOS_3005.Response>> REQ_SOS_3005(final SOS_3005.Request reqData) {
        RES_SOS_3005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3005.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3005, reqData);

        return RES_SOS_3005;
    }

    public MutableLiveData<NetUIResponse<SOS_3006.Response>> REQ_SOS_3006(final SOS_3006.Request reqData) {
        RES_SOS_3006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3006.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3006, reqData);

        return RES_SOS_3006;
    }

    public MutableLiveData<NetUIResponse<SOS_3007.Response>> REQ_SOS_3007(final SOS_3007.Request reqData) {
        RES_SOS_3007.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3007.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3007.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3007.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3007, reqData);

        return RES_SOS_3007;
    }

    public MutableLiveData<NetUIResponse<SOS_3008.Response>> REQ_SOS_3008(final SOS_3008.Request reqData) {
        RES_SOS_3008.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SOS_3008.setValue(NetUIResponse.success(new Gson().fromJson(object, SOS_3008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SOS_3008.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SOS_3008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_SOS_3008, reqData);

        return RES_SOS_3008;
    }
}
