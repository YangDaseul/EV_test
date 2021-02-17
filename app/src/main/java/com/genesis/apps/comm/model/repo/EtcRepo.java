package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.etc.AbnormalCheck;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class EtcRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<AbnormalCheck.Response>> RES_ABNORMAL_CHECK = new MutableLiveData<>();

    @Inject
    public EtcRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<AbnormalCheck.Response>> REQ_ABNORMAL_CHECK(final AbnormalCheck.Request reqData) {
        RES_ABNORMAL_CHECK.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_ABNORMAL_CHECK.setValue(NetUIResponse.success(new Gson().fromJson(object, AbnormalCheck.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_ABNORMAL_CHECK.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_ABNORMAL_CHECK.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.ETC_ABNORMAL_CHECK_URL[GAInfo.SERVER_TYPE], APIInfo.ETC_ABNORMAL_CHECK, reqData);

        return RES_ABNORMAL_CHECK;
    }

}
