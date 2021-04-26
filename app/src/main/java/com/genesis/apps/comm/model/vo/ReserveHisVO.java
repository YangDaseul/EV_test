package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전기 예약신청내역
 * @see #sid 충전소ID
 * @see #chgName 충전소명
 * @see #csupport 충전기지원타입
 * @see #reservNo 예약번호
 * @see #reservDtm 예약일시
 * @see #reservStusCd 예약상태코드
 * @see #chgAmt 충전금액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReserveHisVO extends BaseData {
    @Expose
    @SerializedName("sid")
    private String sid;
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("csupport")
    private String csupport;
    @Expose
    @SerializedName("reservNo")
    private String reservNo;
    @Expose
    @SerializedName("reservDtm")
    private String reservDtm;
    @Expose
    @SerializedName("reservStusCd")
    private String reservStusCd;
    @Expose
    @SerializedName("chgAmt")
    private String chgAmt;

} // end of class ReserveHisVO
