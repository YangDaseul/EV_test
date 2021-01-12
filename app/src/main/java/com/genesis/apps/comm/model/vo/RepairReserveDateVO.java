package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Ignore;
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
 * @see #rsvtTm 예약시간
 * HH24MI
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairReserveDateVO extends BaseData {

    public RepairReserveDateVO(){

    }

    @Expose
    @SerializedName("rsvtDt")
    private String rsvtDt;
    @Expose
    @SerializedName("dayCd")
    private String dayCd;
    @Expose
    @SerializedName("rsvtTm")
    private String rsvtTm;

    @Ignore
    private boolean isSelect;
}
