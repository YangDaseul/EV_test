package com.genesis.apps.comm.model.vo.map;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public @Data class ReverseGeocodingReqVO extends BaseData {
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lon")
    private double lon;
    @Expose
    @SerializedName("addrType")
    private int addrType;
}
