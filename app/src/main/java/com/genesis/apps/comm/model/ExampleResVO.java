package com.genesis.apps.comm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data class ExampleResVO extends BaseData {

    @Expose
    @SerializedName("requestID")
    private String requestID;

    @Expose
    @SerializedName("value")
    private int value;

}
