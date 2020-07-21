package com.genesis.apps.comm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data class ExampleReqVO extends BaseData {

    @Expose
    @SerializedName("requestID")
    private String requestID;

    @Expose
    @SerializedName("value")
    private int value;

}
