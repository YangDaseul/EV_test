package com.genesis.apps.comm.model.vo.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @brief 최종 교환 정보
 * @author hjpark
 * @see #replacementDate 교환일시
 * @see #updateDate 데이터 업데이트 일시
 * @see #odometer 교환시점 누적주행거리
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class LastinfoVO extends BaseData {
    @Expose
    @SerializedName("replacementDate")
    private String replacementDate;
    @Expose
    @SerializedName("updateDate")
    private String updateDate;
    @Expose
    @SerializedName("odometer")
    private String odometer;

    private int temp;
}
