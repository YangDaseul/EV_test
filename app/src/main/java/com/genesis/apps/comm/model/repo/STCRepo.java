package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.api.gra.STC_1002;
import com.genesis.apps.comm.model.api.gra.STC_1003;
import com.genesis.apps.comm.model.api.gra.STC_1004;
import com.genesis.apps.comm.model.api.gra.STC_1005;
import com.genesis.apps.comm.model.api.gra.STC_1006;
import com.genesis.apps.comm.model.api.gra.STC_2001;
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

    public final MutableLiveData<NetUIResponse<STC_2001.Response>> RES_STC_2001 = new MutableLiveData<>();

    @Inject
    public STCRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<STC_1001.Response>> REQ_STC_1001(final STC_1001.Request reqData) {
        RES_STC_1001.setValue(NetUIResponse.loading(null));
//        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"test message\",\"reservList\":[{\"sid\":\"123456\",\"chgName\":\"현대EV스테이션 현대EV스테이션 현대EV 스테이션 강남\",\"dist\":\"11.11\",\"reservYn\":\"Y\",\"carPayUseYn\":\"Y\",\"lat\":\"37.479928\",\"lot\":\"126.883741\",\"superSpeedCnt\":\"2\",\"highSpeedCnt\":\"1\",\"slowSpeedCnt\":\"7\"}],\"searchList\":[{\"sid\":\"234567\",\"chgName\":\"제네시스 EV 스테이션\",\"dist\":\"22.1\",\"reservYn\":\"Y\",\"carPayUseYn\":\"N\",\"lat\":\"37.479875\",\"lot\":\"126.884081\",\"superSpeedCnt\":\"1\",\"highSpeedCnt\":\"0\",\"slowSpeedCnt\":\"11\"},{\"sid\":\"11234567\",\"chgName\":\"제네시스 EV 스테이션 22\",\"dist\":\"11\",\"reservYn\":\"Y\",\"carPayUseYn\":\"N\",\"lat\":\"37.484728\",\"lot\":\"126.881471\",\"superSpeedCnt\":\"1\",\"highSpeedCnt\":\"0\",\"slowSpeedCnt\":\"11\"}]}";
//        RES_STC_1001.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1001.Response.class)));
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
        return RES_STC_1001;
    }

    public MutableLiveData<NetUIResponse<STC_1002.Response>> REQ_STC_1002(final STC_1002.Request reqData) {
        RES_STC_1002.setValue(NetUIResponse.loading(null));
//        String dummyData = "";
//        RES_STC_1002.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1002.Response.class)));
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


        return RES_STC_1002;
    }

    public MutableLiveData<NetUIResponse<STC_1003.Response>> REQ_STC_1003(final STC_1003.Request reqData) {
        RES_STC_1003.setValue(NetUIResponse.loading(null));
//        String dummyData = "";
//        RES_STC_1003.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1003.Response.class)));

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


        return RES_STC_1003;
    }

    public MutableLiveData<NetUIResponse<STC_1004.Response>> REQ_STC_1004(final STC_1004.Request reqData) {
        RES_STC_1004.setValue(NetUIResponse.loading(null));
//        String dummyData = "";
//        RES_STC_1004.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1004.Response.class)));
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

        return RES_STC_1004;
    }

    public MutableLiveData<NetUIResponse<STC_1005.Response>> REQ_STC_1005(final STC_1005.Request reqData) {
        RES_STC_1005.setValue(NetUIResponse.loading(null));

//        String dummyData = "{\n" +
//                "  \"rtCd\": \"0000\",\n" +
//                "  \"rtMsg\": \"Success\",\n" +
//                "  \"reservList\": [\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명1\",\n" +
//                "      \"csupport\": \"001\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명2\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104142300\",\n" +
//                "      \"reservStusCd\": \"2000\",\n" +
//                "      \"chgAmt\": \"1500000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명충전소명충전소명충전소명충전소명충전소명충전소명충전소명충전소명충전소명3\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"3000\",\n" +
//                "      \"chgAmt\": \"150000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명4\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명5\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"2000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명6\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"3000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명7\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명8\",\n" +
//                "      \"csupport\": \"010\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명9\",\n" +
//                "      \"csupport\": \"001\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202104151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"chgName\": \"충전소명10\",\n" +
//                "      \"csupport\": \"100\",\n" +
//                "      \"reservNo\": \"SAMPLE\",\n" +
//                "      \"reservDtm\": \"202103151300\",\n" +
//                "      \"reservStusCd\": \"1000\",\n" +
//                "      \"chgAmt\": \"50050\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//        RES_STC_1005.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1005.Response.class)));
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

        return RES_STC_1005;
    }

    public MutableLiveData<NetUIResponse<STC_1006.Response>> REQ_STC_1006(final STC_1006.Request reqData) {
        RES_STC_1006.setValue(NetUIResponse.loading(null));

//        String dummyData = "";
//        RES_STC_1006.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, STC_1006.Response.class)));

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


        return RES_STC_1006;
    }

    public MutableLiveData<NetUIResponse<STC_2001.Response>> REQ_STC_2001(final STC_2001.Request reqData) {
        RES_STC_2001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_STC_2001.setValue(NetUIResponse.success(new Gson().fromJson(object, STC_2001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_STC_2001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_STC_2001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_STC_2001, reqData);
        return RES_STC_2001;
    }
    
} // end of class STCRepo
