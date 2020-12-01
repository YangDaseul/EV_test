package com.genesis.apps.comm.model.vo.developers;

import androidx.room.Entity;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 차량 연결(등록)
 * @see #carId 차량 고유 id
 */

@Entity(primaryKeys = {"vin"})
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CarConnectVO extends BaseData {

    public CarConnectVO(){

    }

    @Expose
    @SerializedName("carId")
    private String carId;
    @Expose
    @SerializedName("carGrantType")
    private String carGrantType;
    @Expose
    @SerializedName("carName")
    private String carName;
    @Expose
    @SerializedName("vin")
    private String vin;
}
