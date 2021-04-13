package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1001;
import com.genesis.apps.comm.model.api.gra.CHB_1002;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.CHB_1007;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class CHBRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<CHB_1001.Response>> RES_CHB_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1002.Response>> RES_CHB_1002 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<CHB_1006.Response>> RES_CHB_1006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1007.Response>> RES_CHB_1007 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1008.Response>> RES_CHB_1008 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1009.Response>> RES_CHB_1009 = new MutableLiveData<>();

    public final MutableLiveData<NetUIResponse<CHB_1015.Response>> RES_CHB_1015 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1016.Response>> RES_CHB_1016 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CHB_1017.Response>> RES_CHB_1017 = new MutableLiveData<>();



    @Inject
    public CHBRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<CHB_1001.Response>> REQ_CHB_1001(final CHB_1001.Request reqData) {
        RES_CHB_1001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHB_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, CHB_1001.Response.class)));

//                RES_CHB_1001.setValue(NetUIResponse.success(new Gson().fromJson("{\n" +
//                        "  \"rtCd\": \"0000\",\n" +
//                        "  \"rtMsg\": \"Success\",\n" +
//                        "  \"status\": \"Y\",\n" +
//                        "  \"statusNm\": \"예약완료\",\n" +
//                        "}", CHB_1001.Response.class)));

            }

            @Override
            public void onFail(NetResult e) {
                RES_CHB_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1001.setValue(NetUIResponse.success(TestCode.CHB_1001));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1001, reqData);

        return RES_CHB_1001;
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
                RES_CHB_1007.setValue(NetUIResponse.error(e.getMseeage(), null));
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
                RES_CHB_1008.setValue(NetUIResponse.error(e.getMseeage(), null));
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
                RES_CHB_1009.setValue(NetUIResponse.error(e.getMseeage(), null));
//                RES_CHB_1009.setValue(NetUIResponse.success(TestCode.CHB_1009));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHB_1009.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_CHB_1009, reqData);

        return RES_CHB_1009;
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
}
