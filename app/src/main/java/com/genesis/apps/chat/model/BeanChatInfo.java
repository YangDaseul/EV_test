package com.genesis.apps.chat.model;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data class BeanChatInfo extends BaseData {
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("nextWebbUrl")
    private String nextWebbUrl;
    @Expose
    @SerializedName("chatUrl")
    private String chatUrl;
    @Expose
    @SerializedName("listUrl")
    private int listUrl;
    @Expose
    @SerializedName("listJson")
    private int listJson;
}
