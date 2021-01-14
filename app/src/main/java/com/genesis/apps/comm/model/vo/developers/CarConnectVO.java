package com.genesis.apps.comm.model.vo.developers;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 차량 연결(등록)
 * @see #carId 차량 고유 id
 */

@Entity
@EqualsAndHashCode(callSuper = false)
public @Data
class CarConnectVO extends BaseData {

    public CarConnectVO(String vin){
        this.vin = StringUtil.isValidString(vin);
    }

    public CarConnectVO(@NonNull String vin, String carId, String masterCarId, String carGrantType, String carName){
        this.vin = vin;
        this.carId = carId;
        this.masterCarId = masterCarId;
        this.carGrantType = carGrantType;
        this.carName = carName;
    }


    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("carId")
    private String carId;
    @Expose
    @SerializedName("masterCarId")
    private String masterCarId;
    @Expose
    @SerializedName("carGrantType")
    private String carGrantType;
    @Expose
    @SerializedName("carName")
    private String carName;

    @NonNull
    public String getVin(){
        return vin;
    }

}
