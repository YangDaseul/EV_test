package com.genesis.apps.chat.data;

import com.genesis.apps.comm.data.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataChatInfo extends BaseData {
    @Expose
    @SerializedName("code")
    public int code;
    @Expose
    @SerializedName("msg")
    public String msg;
    @Expose
    @SerializedName("key")
    public String key;
    @Expose
    @SerializedName("nextWebbUrl")
    public String nextWebbUrl;
    @Expose
    @SerializedName("chatUrl")
    public String chatUrl;
    @Expose
    @SerializedName("listUrl")
    public int listUrl;
    @Expose
    @SerializedName("listJson")
    public int listJson;
}
