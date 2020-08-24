package com.genesis.apps.comm.model.map;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief POI 검색

 */
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public @Data class AroundPOIReqVO extends BaseData {
    @Expose
    @SerializedName("depthText")
    private String depthText;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lon")
    private double lon;
    @Expose
    @SerializedName("radius")
    private int radius;
    @Expose
    @SerializedName("sort")
    private int sort;
    @Expose
    @SerializedName("roadType")
    private int roadType;
    @Expose
    @SerializedName("from")
    private int from;
    @Expose
    @SerializedName("size")
    private int size;
}
