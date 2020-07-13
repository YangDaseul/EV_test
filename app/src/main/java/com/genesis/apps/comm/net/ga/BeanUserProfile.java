package com.genesis.apps.comm.net.ga;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data
class BeanUserProfile extends BaseData {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("mobileNum")
    private String mobileNum;
    @Expose
    @SerializedName("birthdate")
    private String birthdate;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("lang")
    private String lang;
    @Expose
    @SerializedName("social")
    private boolean social;
    @Expose
    @SerializedName("customerNumber")
    private String customerNumber;
    @Expose
    @SerializedName("hmcCustNo")
    private String hmcCustNo;
}