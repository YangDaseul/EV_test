package com.genesis.apps.fcm;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
//TODO 2020-07-16 PUSH 데이터 정책에 따라 변경 필요
@EqualsAndHashCode(callSuper=false)
public @Data class PushVO extends BaseData {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("cat")
    private String cat;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("data")
    private String data;
}
