package com.genesis.apps.comm.model.gra.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.MYP_0004;
import com.genesis.apps.comm.model.gra.MYP_0005;
import com.genesis.apps.comm.model.gra.MYP_1003;
import com.genesis.apps.comm.model.gra.MYP_1005;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.MYP_2001;
import com.genesis.apps.comm.model.gra.MYP_2002;
import com.genesis.apps.comm.model.gra.MYP_2003;
import com.genesis.apps.comm.model.gra.MYP_2004;
import com.genesis.apps.comm.model.gra.MYP_2005;
import com.genesis.apps.comm.model.gra.MYP_2006;
import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.MYP_8005;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_0001, reqData);

        return RES_MYP_0001;
    }

    public MutableLiveData<NetUIResponse<MYP_0004.Response>> REQ_MYP_0004(final MYP_0004.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0004.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_0005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_0005.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_1005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_1005.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
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
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_1006.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_1006.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_1006, reqData);

        return RES_MYP_1006;
    }



    public MutableLiveData<NetUIResponse<MYP_2001.Response>> REQ_MYP_2001(final MYP_2001.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2002.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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
    public MutableLiveData<NetUIResponse<MYP_2003.Response>> REQ_MYP_2003(final MYP_2003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2003.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2004.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2004.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_MYP_2005.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_2005.Response.class)));
                //TODO SINGLETON VO에 값 저장?
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
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_2006.setValue(NetUIResponse.error(e.getMseeage(), null));
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

                MYP_8001.Response response = new Gson().fromJson(object, MYP_8001.Response.class); //결과코드 및 결과메시지 저장
                response.setTermVO(new Gson().fromJson(object, TermVO.class)); //term데이터 저장
                RES_MYP_8001.setValue(NetUIResponse.success(response));
//                RES_MYP_8001.setValue(NetUIResponse.success(new Gson().fromJson(object, MYP_8001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8001.setValue(NetUIResponse.error(e.getMseeage(), null));
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
                //TODO SINGLETON VO에 값 저장?
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
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_MYP_8005.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_MYP_8005.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_MYP_8005, reqData);

        return RES_MYP_8005;
    }

}
