package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.CTT_1001;
import com.genesis.apps.comm.model.api.gra.CTT_1002;
import com.genesis.apps.comm.model.api.gra.CTT_1004;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.google.gson.Gson;

import javax.inject.Inject;

public class CTTRepo {

    NetCaller netCaller;
    private DatabaseHolder databaseHolder;

    public final MutableLiveData<NetUIResponse<CTT_1001.Response>> RES_CTT_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CTT_1002.Response>> RES_CTT_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CTT_1004.Response>> RES_CTT_1004 = new MutableLiveData<>();

    @Inject
    public CTTRepo(NetCaller netCaller, DatabaseHolder databaseHolder) {
        this.netCaller = netCaller;
        this.databaseHolder = databaseHolder;
    }

    public MutableLiveData<NetUIResponse<CTT_1001.Response>> REQ_CTT_1001(final CTT_1001.Request reqData) {
        RES_CTT_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                //TODO 2020-11-23 하드코딩 요청으로 처리
//                RES_CTT_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, CTT_1001.Response.class)));

                String message = "{\n" +
                        "  \"rtCd\": \"0000\",\n" +
                        "  \"rtMsg\": \"성공\",\n" +
                        "  \"ttlList\": [\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"1\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2741\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"2\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2749&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"3\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2414&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"4\",\n" +
                        "      \"catCd\": \"2000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2652&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

                RES_CTT_1001.setValue(NetUIResponse.success(new Gson().fromJson(message, CTT_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                //TODO 2020-11-23 하드코딩 요청으로 처리
//                RES_CTT_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
                String message = "{\n" +
                        "  \"rtCd\": \"0000\",\n" +
                        "  \"rtMsg\": \"성공\",\n" +
                        "  \"ttlList\": [\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"1\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2741\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"2\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2749&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"3\",\n" +
                        "      \"catCd\": \"1000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2414&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"listSeqNo\": \"4\",\n" +
                        "      \"catCd\": \"2000\",\n" +
                        "      \"ttImgUri\": \"https://dive.hyundaicard.com/web/content/contentView.hdc?contentId=2652&cookieDiveWeb=Y\",\n" +
                        "      \"dtlViewCd\": \"1000\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

                RES_CTT_1001.setValue(NetUIResponse.success(new Gson().fromJson(message, CTT_1001.Response.class)));
            }

            @Override
            public void onError(NetResult e) {
                RES_CTT_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CTT_1001, reqData);

        return RES_CTT_1001;
    }


    public MutableLiveData<NetUIResponse<CTT_1002.Response>> REQ_CTT_1002(final CTT_1002.Request reqData) {
        RES_CTT_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CTT_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, CTT_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CTT_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CTT_1002.setValue(NetUIResponse.success(TestCode.CTT_1002));
            }

            @Override
            public void onError(NetResult e) {
                RES_CTT_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CTT_1002, reqData);

        return RES_CTT_1002;
    }


    public MutableLiveData<NetUIResponse<CTT_1004.Response>> REQ_CTT_1004(final CTT_1004.Request reqData) {
        RES_CTT_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CTT_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, CTT_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CTT_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CTT_1004.setValue(NetUIResponse.success(TestCode.CTT_1004));
            }

            @Override
            public void onError(NetResult e) {
                RES_CTT_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CTT_1004, reqData);

        return RES_CTT_1004;
    }

}
