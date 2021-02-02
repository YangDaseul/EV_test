package com.genesis.apps.comm.model.vo;

import android.app.Application;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.fcm.FirebaseMessagingService;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Data;

@Singleton
public @Data class DeviceDTO extends BaseData {

    private Application application;
    private String mdn;
    private String pushId;
    private String deviceId;

    @Inject
    public DeviceDTO(Application application){
        this.application = application;
    }

    public void initData(){
        setDeviceId(DeviceUtil.getDeviceId(application));
        setMdn(DeviceUtil.getPhoneNumber(application));
        FirebaseMessagingService.getToken(this::setPushId);
    }
}
