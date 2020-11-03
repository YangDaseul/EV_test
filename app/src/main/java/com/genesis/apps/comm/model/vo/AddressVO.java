package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 주소정보 (hmn)
 * @author hjpark
 */

@Entity(indices = {@Index(value = {"addrRoad"}, unique = true)})
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AddressVO extends BaseData {
    public AddressVO(){

    }
    @PrimaryKey(autoGenerate = true)
    private int _id;
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
