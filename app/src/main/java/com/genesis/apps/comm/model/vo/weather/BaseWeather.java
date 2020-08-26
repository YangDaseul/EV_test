package com.genesis.apps.comm.model.vo.weather;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 기온(T1H) : ℃
 * @author hjpark
 * @see #fcstDtm 예측일자/시각
 * YYYYMMDDHH23MI
 * @see #fcstVal 예측값
 */
public @Data
class BaseWeather extends BaseData {
    @Expose
    @SerializedName("fcstDtm")
    private String fcstDtm;
    @Expose
    @SerializedName("fcstVal")
    private String fcstVal;
}