package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.model.api.gra.MYP_0004;
import com.genesis.apps.comm.model.api.gra.MYP_0005;
import com.genesis.apps.comm.model.api.gra.MYP_1003;
import com.genesis.apps.comm.model.api.gra.MYP_1005;
import com.genesis.apps.comm.model.api.gra.MYP_1006;
import com.genesis.apps.comm.model.api.gra.MYP_2001;
import com.genesis.apps.comm.model.api.gra.MYP_2002;
import com.genesis.apps.comm.model.api.gra.MYP_2003;
import com.genesis.apps.comm.model.api.gra.MYP_2004;
import com.genesis.apps.comm.model.api.gra.MYP_2005;
import com.genesis.apps.comm.model.api.gra.MYP_2006;
import com.genesis.apps.comm.model.api.gra.MYP_8001;
import com.genesis.apps.comm.model.api.gra.MYP_8004;
import com.genesis.apps.comm.model.api.gra.MYP_8005;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class MYPRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<MYP_0001.Response>> RES_MYP_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_0004.Response>> RES_MYP_0004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_0005.Response>> RES_MYP_0005 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<MYP_1003.Response>> RES_MYP_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_1005.Response>> RES_MYP_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_1006.Response>> RES_MYP_1006 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<MYP_2001.Response>> RES_MYP_2001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_2002.Response>> RES_MYP_2002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_2003.Response>> RES_MYP_2003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_2004.Response>> RES_MYP_2004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_2005.Response>> RES_MYP_2005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_2006.Response>> RES_MYP_2006 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<MYP_8005.Response>> RES_MYP_8005 = new MutableLiveData<>();

    @Inject
    public MYPRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<MYP_0001.Response>> REQ_MYP_0001(final MYP_0001.Request reqData) {
        RES_MYP_0001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_MYP_0001.setValue(NetUIResponse.success(TestCode.MYP_0001));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0001, reqData);

        return RES_MYP_0001;
    }

    public MutableLiveData<NetUIResponse<MYP_0004.Response>> REQ_MYP_0004(final MYP_0004.Request reqData) {
        RES_MYP_0004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0004, reqData);

        return RES_MYP_0004;
    }


    public MutableLiveData<NetUIResponse<MYP_0005.Response>> REQ_MYP_0005(final MYP_0005.Request reqData) {
        RES_MYP_0005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0005, reqData);

        return RES_MYP_0005;
    }

    public MutableLiveData<NetUIResponse<MYP_1003.Response>> REQ_MYP_1003(final MYP_1003.Request reqData) {
        RES_MYP_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_1003, reqData);

        return RES_MYP_1003;
    }
    public MutableLiveData<NetUIResponse<MYP_1005.Response>> REQ_MYP_1005(final MYP_1005.Request reqData) {
        RES_MYP_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_MYP_1005.setValue(NetUIResponse.success(TestCode.MYP_1005));
                RES_MYP_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_1005, reqData);

        return RES_MYP_1005;
    }
    public MutableLiveData<NetUIResponse<MYP_1006.Response>> REQ_MYP_1006(final MYP_1006.Request reqData) {
        RES_MYP_1006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1006.Response.class)));
//                                RES_MYP_1006.setValue(NetUIResponse.success(TestCode.MYP_1006));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_MYP_1006.setValue(NetUIResponse.success(TestCode.MYP_1006));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_1006, reqData);

        return RES_MYP_1006;
    }



    public MutableLiveData<NetUIResponse<MYP_2001.Response>> REQ_MYP_2001(final MYP_2001.Request reqData) {
        RES_MYP_2001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2001, reqData);

        return RES_MYP_2001;
    }

    public MutableLiveData<NetUIResponse<MYP_2002.Response>> REQ_MYP_2002(final MYP_2002.Request reqData) {
        RES_MYP_2002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {

//                object = "{\n" +
//                        "  \"rsltCd\": \"0000\",\n" +
//                        "  \"rsltMsg\": \"성공\",\n" +
//                        "  \"blueMbrYn\": \"Y\",\n" +
//                        "  \"mbrshMbrMgmtNo\": \"1000000\",\n" +
//                        "  \"transTotCnt\": \"3\",\n" +
//                        "  \"transList\": [\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"1\",\n" +
//                        "      \"transDtm\": \"20200901111111\",\n" +
//                        "      \"frchsNm\": \"가맹점1\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"124574\",\n" +
//                        "      \"rmndPont\": \"1111111\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"2\",\n" +
//                        "      \"transDtm\": \"20200902222222\",\n" +
//                        "      \"frchsNm\": \"가맹점2\",\n" +
//                        "      \"transTypNm\": \"사용\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"3\",\n" +
//                        "      \"transDtm\": \"20200920000000\",\n" +
//                        "      \"frchsNm\": \"가맹점3\",\n" +
//                        "      \"transTypNm\": \"적립\",\n" +
//                        "      \"useMlg\": \"222222\",\n" +
//                        "      \"rmndPont\": \"333333\"\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "      \"seqNo\": \"4\",\n" +
//                        "      \"transDtm\": \"20200930000000\",\n" +
//                        "      \"frchsNm\": \"가맹점4\",\n" +
//                        "      \"transTypNm\": \"취소\",\n" +
//                        "      \"useMlg\": \"2222221\",\n" +
//                        "      \"rmndPont\": \"3333331\"\n" +
//                        "    }\n" +
//                        "  ]\n" +
//                        "}";
                RES_MYP_2002.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2002, reqData);

        return RES_MYP_2002;
    }
    //사용하지 않는 전문
    public MutableLiveData<NetUIResponse<MYP_2003.Response>> REQ_MYP_2003(final MYP_2003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2003, reqData);

        return RES_MYP_2003;
    }

    public MutableLiveData<NetUIResponse<MYP_2004.Response>> REQ_MYP_2004(final MYP_2004.Request reqData) {
        RES_MYP_2004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2004, reqData);

        return RES_MYP_2004;
    }
    public MutableLiveData<NetUIResponse<MYP_2005.Response>> REQ_MYP_2005(final MYP_2005.Request reqData) {
        RES_MYP_2005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2005, reqData);

        return RES_MYP_2005;
    }
    public MutableLiveData<NetUIResponse<MYP_2006.Response>> REQ_MYP_2006(final MYP_2006.Request reqData) {
        RES_MYP_2006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2006.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2006.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_MYP_2006.setValue(NetUIResponse.success(TestCode.MYP_2006));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_2006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_2006, reqData);

        return RES_MYP_2006;
    }


    public MutableLiveData<NetUIResponse<MYP_8001.Response>> REQ_MYP_8001(final MYP_8001.Request reqData) {
        RES_MYP_8001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                switch (reqData.getTermCd()){
//                    case MyGTermsActivity.TERMS_1000:
//                        RES_MYP_8001.setValue(NetUIResponse.success(TestCode.MYP_8001_1000));
//                        break;
//                    case MyGTermsActivity.TERMS_2000:
//                        RES_MYP_8001.setValue(NetUIResponse.success(TestCode.MYP_8001_2000));
//                        break;
//                    case MyGTermsActivity.TERMS_3000:
//                        RES_MYP_8001.setValue(NetUIResponse.success(TestCode.MYP_8001_3000));
//                        break;
//                    case MyGTermsActivity.TERMS_4000:
//                        RES_MYP_8001.setValue(NetUIResponse.success(TestCode.MYP_8001_4000));
//                        break;
//                    case MyGTermsActivity.TERMS_5000:
//                    default:
//                        RES_MYP_8001.setValue(NetUIResponse.success(TestCode.MYP_8001_5000));
//
//                }


            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8001, reqData);

        return RES_MYP_8001;
    }

    public MutableLiveData<NetUIResponse<MYP_8004.Response>> REQ_MYP_8004(final MYP_8004.Request reqData) {
        RES_MYP_8004.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8004, reqData);

        return RES_MYP_8004;
    }

    public MutableLiveData<NetUIResponse<MYP_8005.Response>> REQ_MYP_8005(final MYP_8005.Request reqData) {
        RES_MYP_8005.setValue(NetUIResponse.loading(null));

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_8005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8005.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_MYP_8005.setValue(NetUIResponse.success(TestCode.MYP_8005));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8005, reqData);

        return RES_MYP_8005;
    }

}
