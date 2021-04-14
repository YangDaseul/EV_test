package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전기 예약가능시간
 * @see #reservDt 예약시간
 * HH24MI(30분단위)
 * @see #reservYn 가능여부
 * Y:예약가능 N:예약불가
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReserveDtVO extends BaseData {
    @Expose
    @SerializedName("reservDt")
    private String reservDt;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
} // end of class ReserveDtVO
