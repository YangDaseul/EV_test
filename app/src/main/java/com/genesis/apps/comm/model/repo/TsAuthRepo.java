package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.tsauth.GetCheckCarOwnerShip;
import com.genesis.apps.comm.model.api.tsauth.GetNewCarOwnerShip;
import com.genesis.apps.comm.model.constants.TsAuthInfo;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class TsAuthRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<GetNewCarOwnerShip.Response>> RES_GET_NEW_CAR_OWNER_SHIP = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<GetCheckCarOwnerShip.Response>> RES_GET_CHECK_CAR_OWNER_SHIP = new MutableLiveData<>();

    @Inject
    public TsAuthRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<GetNewCarOwnerShip.Response>> REQ_GET_NEW_CAR_OWNER_SHIP(final GetNewCarOwnerShip.Request reqData) {
        RES_GET_NEW_CAR_OWNER_SHIP.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GET_NEW_CAR_OWNER_SHIP.setValue(NetUIResponse.success(new Gson().fromJson(object, GetNewCarOwnerShip.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GET_NEW_CAR_OWNER_SHIP.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GET_NEW_CAR_OWNER_SHIP.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, TsAuthInfo.TS_AUTH_URL, APIInfo.TSAUTH_GET_NEW_CAR_OWNER_SHIP, reqData);

        return RES_GET_NEW_CAR_OWNER_SHIP;
    }

    public MutableLiveData<NetUIResponse<GetCheckCarOwnerShip.Response>> REQ_GET_CHECK_CAR_OWNER_SHIP(final GetCheckCarOwnerShip.Request reqData) {
        RES_GET_CHECK_CAR_OWNER_SHIP.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_GET_CHECK_CAR_OWNER_SHIP.setValue(NetUIResponse.success(new Gson().fromJson(object, GetCheckCarOwnerShip.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_GET_CHECK_CAR_OWNER_SHIP.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_GET_CHECK_CAR_OWNER_SHIP.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, TsAuthInfo.TS_AUTH_URL, APIInfo.TSAUTH_GET_CHECK_CAR_OWNER_SHIP, reqData);

        return RES_GET_CHECK_CAR_OWNER_SHIP;
    }

}
