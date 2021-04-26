package com.genesis.apps.comm.model.vo;


import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전소 조회
 * @see #sid 충전소ID
 * @see #chgName 충전소명
 * @see #dist 거리(단위:KM)
 * @see #reservYn 예약가능여부
 * @see #carPayUseYn 카페이사용가능여부
 * @see #lat 충전소위치-위도
 * @see #lot 충전소위치-경도
 * @see #superSpeedCnt 초고속충전기수
 * @see #highSpeedCnt 급속충전기수
 * @see #slowSpeedCnt 완속충전기수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReserveVo extends BaseData {
    @Expose
    @SerializedName("sid")
    private String sid;
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
    @Expose
    @SerializedName("carPayUseYn")
    private String carPayUseYn;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("superSpeedCnt")
    private String superSpeedCnt;
    @Expose
    @SerializedName("highSpeedCnt")
    private String highSpeedCnt;
    @Expose
    @SerializedName("slowSpeedCnt")
    private String slowSpeedCnt;
} // end of class ReservVo
