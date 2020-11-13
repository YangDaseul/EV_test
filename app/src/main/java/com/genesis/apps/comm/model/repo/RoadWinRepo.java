package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.roadwin.CheckPrice;
import com.genesis.apps.comm.model.api.roadwin.ServiceAreaCheck;
import com.genesis.apps.comm.model.api.roadwin.Work;
import com.genesis.apps.comm.model.constants.RoadWinInfo;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class RoadWinRepo {

    NetCaller netCaller;

    
    public final MutableLiveData<NetUIResponse<CheckPrice.Response>> RES_CHECK_PRICE = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<ServiceAreaCheck.Response>> RES_SERVICE_AREA_CHECK = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Work.Response>> RES_WORK = new MutableLiveData<>();

    @Inject
    public RoadWinRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<CheckPrice.Response>> REQ_CHECK_PRICE(final CheckPrice.Request reqData) {
        RES_CHECK_PRICE.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CHECK_PRICE.setValue(NetUIResponse.success(new Gson().fromJson(object, CheckPrice.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CHECK_PRICE.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CHECK_PRICE.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, RoadWinInfo.ROADWIN_URL, APIInfo.ROADWIN_CHECK_PRICE, reqData);

        return RES_CHECK_PRICE;
    }


    public MutableLiveData<NetUIResponse<ServiceAreaCheck.Response>> REQ_SERVICE_AREA_CHECK(final ServiceAreaCheck.Request reqData) {
        RES_SERVICE_AREA_CHECK.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_SERVICE_AREA_CHECK.setValue(NetUIResponse.success(new Gson().fromJson(object, ServiceAreaCheck.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_SERVICE_AREA_CHECK.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_SERVICE_AREA_CHECK.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, RoadWinInfo.ROADWIN_URL, APIInfo.ROADWIN_SERVICE_AREA_CHECK, reqData);

        return RES_SERVICE_AREA_CHECK;
    }


    public MutableLiveData<NetUIResponse<Work.Response>> REQ_WORK(final Work.Request reqData) {
        RES_WORK.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_WORK.setValue(NetUIResponse.success(new Gson().fromJson(object, Work.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_WORK.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_WORK.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, RoadWinInfo.ROADWIN_URL, APIInfo.ROADWIN_WORK, reqData);

        return RES_WORK;
    }

}
