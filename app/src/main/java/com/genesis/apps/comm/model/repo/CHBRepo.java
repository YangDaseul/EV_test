package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1002;
import com.genesis.apps.comm.model.api.gra.CHB_1003;
import com.genesis.apps.comm.model.api.gra.CHB_1004;
import com.genesis.apps.comm.model.api.gra.CHB_1005;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.CHB_1007;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1010;
import com.genesis.apps.comm.model.api.gra.CHB_1011;
import com.genesis.apps.comm.model.api.gra.CHB_1013;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.model.api.gra.CHB_1019;
import com.genesis.apps.comm.model.api.gra.CHB_1020;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.api.gra.CHB_1022;
import com.genesis.apps.comm.model.api.gra.CHB_1023;
import com.genesis.apps.comm.model.api.gra.CHB_1024;
import com.genesis.apps.comm.model.api.gra.CHB_1025;
import com.genesis.apps.comm.model.api.gra.CHB_1026;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class CHBRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<CHB_1002.Response>> RES_CHB_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1003.Response>> RES_CHB_1003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1004.Response>> RES_CHB_1004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1005.Response>> RES_CHB_1005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1006.Response>> RES_CHB_1006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1007.Response>> RES_CHB_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1008.Response>> RES_CHB_1008 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1009.Response>> RES_CHB_1009 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1010.Response>> RES_CHB_1010 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<CHB_1011.Response>> RES_CHB_1011 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1013.Response>> RES_CHB_1013 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1015.Response>> RES_CHB_1015 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1016.Response>> RES_CHB_1016 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1017.Response>> RES_CHB_1017 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<CHB_1019.Response>> RES_CHB_1019 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1020.Response>> RES_CHB_1020 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1021.Response>> RES_CHB_1021 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1022.Response>> RES_CHB_1022 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1023.Response>> RES_CHB_1023 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1024.Response>> RES_CHB_1024 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1025.Response>> RES_CHB_1025 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1026.Response>> RES_CHB_1026 = new MutableLiveData<>();


    @Inject
    public CHBRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<CHB_1002.Response>> REQ_CHB_1002(final CHB_1002.Request reqData) {
        RES_CHB_1002.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1002, reqData);

        return RES_CHB_1002;
    }

    public MutableLiveData<NetUIResponse<CHB_1003.Response>> REQ_CHB_1003(final CHB_1003.Request reqData) {
        RES_CHB_1003.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1003, reqData);

        return RES_CHB_1003;
    }

    public MutableLiveData<NetUIResponse<CHB_1004.Response>> REQ_CHB_1004(final CHB_1004.Request reqData) {
        RES_CHB_1004.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1004.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1004.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1004.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1004.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1004, reqData);

        return RES_CHB_1004;
    }

    public MutableLiveData<NetUIResponse<CHB_1005.Response>> REQ_CHB_1005(final CHB_1005.Request reqData) {
        RES_CHB_1005.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1005.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1005, reqData);

        return RES_CHB_1005;
    }

    public MutableLiveData<NetUIResponse<CHB_1006.Response>> REQ_CHB_1006(final CHB_1006.Request reqData) {
        RES_CHB_1006.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1006.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1006.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1006, reqData);

        return RES_CHB_1006;
    }

    public MutableLiveData<NetUIResponse<CHB_1007.Response>> REQ_CHB_1007(final CHB_1007.Request reqData) {
        RES_CHB_1007.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1007.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1007.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1007.setValue(NetUIResponse.success(TestCode.CHB_1007));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1007.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1007, reqData);

        return RES_CHB_1007;
    }

    public MutableLiveData<NetUIResponse<CHB_1008.Response>> REQ_CHB_1008(final CHB_1008.Request reqData) {
        RES_CHB_1008.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1008.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1008.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1008.setValue(NetUIResponse.success(TestCode.CHB_1008));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1008.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1008, reqData);

        return RES_CHB_1008;
    }

    public MutableLiveData<NetUIResponse<CHB_1009.Response>> REQ_CHB_1009(final CHB_1009.Request reqData) {
        RES_CHB_1009.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1009.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1009.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1009.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1009.setValue(NetUIResponse.success(TestCode.CHB_1009));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1009.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1009, reqData);

        return RES_CHB_1009;
    }

    public MutableLiveData<NetUIResponse<CHB_1010.Response>> REQ_CHB_1010(final CHB_1010.Request reqData) {
        RES_CHB_1010.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1010.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1010.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1010.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1010.setValue(NetUIResponse.success(TestCode.CHB_1010));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1010.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1010, reqData);

        return RES_CHB_1010;
    }

    public MutableLiveData<NetUIResponse<CHB_1011.Response>> REQ_CHB_1011(final CHB_1011.Request reqData) {
        RES_CHB_1011.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1011.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1011.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1011.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1011.setValue(NetUIResponse.success(TestCode.CHB_1011));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1011.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1011, reqData);

        return RES_CHB_1011;
    }

    public MutableLiveData<NetUIResponse<CHB_1013.Response>> REQ_CHB_1013(final CHB_1013.Request reqData) {
        RES_CHB_1013.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1013.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1013.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1013.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1013.setValue(NetUIResponse.success(TestCode.CHB_1013));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1013.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1013, reqData);

        return RES_CHB_1013;
    }

    public MutableLiveData<NetUIResponse<CHB_1015.Response>> REQ_CHB_1015(final CHB_1015.Request reqData) {
        RES_CHB_1015.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"\",\"signInYN\":\"Y\",\"cardCount\":4,\"cardList\":[{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid01\",\"cardCoCode\":\"C002\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"KB카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid02\",\"cardCoCode\":\"C003\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"하나카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid03\",\"cardCoCode\":\"C004\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"삼성카드\",\"mainCardYN\":\"Y\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"},{\"cardType\":\"C\",\"cardId\":\"testid00\",\"cardCoCode\":\"C001\",\"cardNo\":\"0000 0000 0000 0000\",\"cardName\":\"BC카드\",\"mainCardYN\":\"N\",\"registerDt\":\"\",\"cardImageUrl\":\"\",\"lastUsedCardYN\":\"Y\",\"oneCardCode\":\"Y\",\"plccYN\":\"Y\"}]}";
        RES_CHB_1015.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, CHB_1015.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1015.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1015.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1015.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1015.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1015, reqData);
        */

        return RES_CHB_1015;
    }

    public MutableLiveData<NetUIResponse<CHB_1016.Response>> REQ_CHB_1016(final CHB_1016.Request reqData) {
        RES_CHB_1016.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"test message\"}";
        RES_CHB_1016.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, CHB_1016.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1016.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1016.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1016.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1016.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1016, reqData);
         */

        return RES_CHB_1016;
    }

    public MutableLiveData<NetUIResponse<CHB_1017.Response>> REQ_CHB_1017(final CHB_1017.Request reqData) {
        RES_CHB_1017.setValue(NetUIResponse.loading(null));

        // TODO 테스트 데이터. API가 정상 작동시 삭제 필요.
        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"test message\"}";
        RES_CHB_1016.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, CHB_1016.Response.class)));

        /*
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1017.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1017.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1017.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1017.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1017, reqData);
         */

        return RES_CHB_1017;
    }

    public MutableLiveData<NetUIResponse<CHB_1019.Response>> REQ_CHB_1019(final CHB_1019.Request reqData) {
        RES_CHB_1019.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1019.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1019.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1019.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1019.setValue(NetUIResponse.success(TestCode.CHB_1019));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1019.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1019, reqData);

        return RES_CHB_1019;
    }

    public MutableLiveData<NetUIResponse<CHB_1020.Response>> REQ_CHB_1020(final CHB_1020.Request reqData) {
        RES_CHB_1020.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1020.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1020.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1020.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1020.setValue(NetUIResponse.success(TestCode.CHB_1020));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1020.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1020, reqData);

        return RES_CHB_1020;
    }


    public MutableLiveData<NetUIResponse<CHB_1021.Response>> REQ_CHB_1021(final CHB_1021.Request reqData) {
        RES_CHB_1021.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1021.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1021.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1021.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1021.setValue(NetUIResponse.success(TestCode.CHB_1021));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1021.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1021, reqData);

        return RES_CHB_1021;
    }

    public MutableLiveData<NetUIResponse<CHB_1022.Response>> REQ_CHB_1022(final CHB_1022.Request reqData) {
        RES_CHB_1022.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1022.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1022.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1022.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1022.setValue(NetUIResponse.success(TestCode.CHB_1022));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1022.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1022, reqData);

        return RES_CHB_1022;
    }

    public MutableLiveData<NetUIResponse<CHB_1023.Response>> REQ_CHB_1023(final CHB_1023.Request reqData) {
        RES_CHB_1023.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1023.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1023.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1023.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1023.setValue(NetUIResponse.success(TestCode.CHB_1023));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1023.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1023, reqData);

        return RES_CHB_1023;
    }

    public MutableLiveData<NetUIResponse<CHB_1024.Response>> REQ_CHB_1024(final CHB_1024.Request reqData) {
        RES_CHB_1024.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1024.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1024.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1024.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1024.setValue(NetUIResponse.success(TestCode.CHB_1024));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1024.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1024, reqData);

        return RES_CHB_1024;
    }

    public MutableLiveData<NetUIResponse<CHB_1025.Response>> REQ_CHB_1025(final CHB_1025.Request reqData) {
        RES_CHB_1025.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1025.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1025.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1025.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1025.setValue(NetUIResponse.success(TestCode.CHB_1025));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1025.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1025, reqData);

        return RES_CHB_1025;
    }

    public MutableLiveData<NetUIResponse<CHB_1026.Response>> REQ_CHB_1026(final CHB_1026.Request reqData) {
        RES_CHB_1026.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1026.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1026.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
//                RES_CHB_1026.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_CHB_1026.setValue(NetUIResponse.success(TestCode.CHB_1026));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1026.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1026, reqData);

        return RES_CHB_1026;
    }
}
