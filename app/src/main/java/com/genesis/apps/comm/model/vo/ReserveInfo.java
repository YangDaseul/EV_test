package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전기 예약하기
 * @see #chgName 예약정보
 * @see #daddr 도로명주소
 * @see #daddrDtl 도로명주소상세
 * @see #bname 운영기관명칭
 * @see #bcall 운영기관연락처
 * @see #csupport 충전기지원타입
 * 100: 완속
 * 010: 급속
 * 001: 초급속
 * @see #reservNo 예약번호
 * @see #reservDtm 예약일시
 * YYYYMMDDHH24MI
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReserveInfo extends BaseData {
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("daddr")
    private String daddr;
    @Expose
    @SerializedName("daddrDtl")
    private String daddrDtl;
    @Expose
    @SerializedName("bname")
    private String bname;
    @Expose
    @SerializedName("bcall")
    private String bcall;
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
    @SerializedName("cid")
    private String cid;
} // end of class ReserveInfo
