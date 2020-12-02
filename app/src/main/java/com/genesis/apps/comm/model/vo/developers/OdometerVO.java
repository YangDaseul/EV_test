package com.genesis.apps.comm.model.vo.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 최신 업데이트 누적운행거리
 * @author hjpark
 * @see #timestamp 누적주행거리 업데이트  일시(YYYYMMDDHHmmSS)
 * @see #value 거리 수치
 * @see #unit 단위
 * (0: feet, 1: km, 2: meter, 3: miles)
 * @see #date 조회일자
 * (YYYYMMDD)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class OdometerVO extends BaseData {
    @Expose
    @SerializedName("timestamp")
    private String timestamp;
    @Expose
    @SerializedName("value")
    private double value;
    @Expose
    @SerializedName("unit")
    private int unit;
    @Expose
    @SerializedName("date")
    private String date;
}
