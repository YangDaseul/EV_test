package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


public class WeatherPointReqVO extends BaseData {

    @Expose
    @SerializedName("requestID")
    private String requestID;

    @Expose
    @SerializedName("rtnCode")
    private int rtnCode;

}
