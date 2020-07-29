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
    private Msg msg;
    @Expose
    @SerializedName("data")
    private PushData data;

    public @Data class Msg extends BaseData {
        @Expose
        @SerializedName("head")
        private String head;
        @Expose
        @SerializedName("body")
        private String body;
    }

    public @Data class PushData extends BaseData {
        @Expose
        @SerializedName("data1")
        private String data1;
        @Expose
        @SerializedName("data2")
        private String data2;
        @Expose
        @SerializedName("img1")
        private String img1;
        @Expose
        @SerializedName("img2")
        private String img2;
    }
}
