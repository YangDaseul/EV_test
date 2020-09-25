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
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("notification")
    private Notification notification;
    @Expose
    @SerializedName("data")
    private PushData data;

    public @Data static class Notification extends BaseData {
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("body")
        private String body;
        @Expose
        @SerializedName("badge")
        private String badge;
    }

    public @Data static class PushData extends BaseData {
        @Expose
        @SerializedName("msgLnkCd")
        private String msgLnkCd;
        @Expose
        @SerializedName("msgLnkUri")
        private String msgLnkUri;
        @Expose
        @SerializedName("dtlLnkCd")
        private String dtlLnkCd;
        @Expose
        @SerializedName("dtlLnkUri")
        private String dtlLnkUri;
        @Expose
        @SerializedName("imgIncsYn")
        private String imgIncsYn;
        @Expose
        @SerializedName("imgFilUri1")
        private String imgFilUri1;
        @Expose
        @SerializedName("imgFilUri2")
        private String imgFilUri2;
        @Expose
        @SerializedName("imgFilUri3")
        private String imgFilUri3;
    }
}
