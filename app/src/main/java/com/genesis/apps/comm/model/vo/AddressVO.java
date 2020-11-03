package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 우편번호
 * @author hjpark
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AddressVO extends BaseData {
    @Expose
    @SerializedName("poiId")
    private int poiId;
    @Expose
    @SerializedName("pid")
    private String pid;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("cname")
    private String cname;
    @Expose
    @SerializedName("addr")
    private String addr;
    @Expose
    @SerializedName("addrRoad")
    private String addrRoad;
    @Expose
    @SerializedName("centerLat")
    private double centerLat;
    @Expose
    @SerializedName("centerLon")
    private double centerLon;
}
