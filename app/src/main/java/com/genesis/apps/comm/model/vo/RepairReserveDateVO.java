package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 홈투홈 예약일자 리스트
 * @see #rsvtDt  예약일
 * YYYYMMDD
 * @see #dayCd  요일코드
 * 1: 월 2:화 3:수 4 : 목 5: 금 6:토 7:일
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairReserveDateVO extends BaseData {
    @Expose
    @SerializedName("rsvtDt")
    private String rsvtDt;
    @Expose
    @SerializedName("dayCd")
    private String dayCd;
}
