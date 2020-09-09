package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 긴급출동 신청 상태 정보
 * @author hjpark
 * @see #sAllocNm 출동자성명
 * @see #controlTel 출동자연락처
 * @see #carNo 출동자차량번호
 * @see #carTypeNm 출동자차량총명
 * @see #receiveDtm 좌표수신일시
 * @see #gXpos 출동자 현재위치 X (WGS84 좌표계)
 * @see #gYpos 출동자 현재위치 Y (WGS84 좌표계)
 * @see #gCustX 고객 현재위치 X (WGS84 좌표계)
 * @see #gcustY 고객 현재위치 Y (WGS84 좌표계)
 * @see #startX 경로탐색 용도 시작 위치 X
 * 출동자 현재위치 X (Beseesl 좌표계) * 360000
 * @see #startY 경로탐색 용도 시작 위치 Y
 * 출동자 현재위치 Y (Beseesl 좌표계) * 360000
 * @see #finishX 경로탐색 용도 종료 위치 X
 * 고객 현재위치 X (Beseesl 좌표계) * 360000
 * @see #finishY 경로탐색 용도 종료 위치 Y
 * 고객 현재위치 Y (Beseesl 좌표계) * 360000
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SOSDriverVO extends BaseData {
    @Expose
    @SerializedName("sAllocNm")
    private String sAllocNm;
    @Expose
    @SerializedName("controlTel")
    private String controlTel;
    @Expose
    @SerializedName("carNo")
    private String carNo;
    @Expose
    @SerializedName("carTypeNm")
    private String carTypeNm;
    @Expose
    @SerializedName("receiveDtm")
    private String receiveDtm;
    @Expose
    @SerializedName("gXpos")
    private String gXpos;
    @Expose
    @SerializedName("gYpos")
    private String gYpos;
    @Expose
    @SerializedName("gCustX")
    private String gCustX;
    @Expose
    @SerializedName("gcustY")
    private String gcustY;
    @Expose
    @SerializedName("startX")
    private String startX;
    @Expose
    @SerializedName("startY")
    private String startY;
    @Expose
    @SerializedName("finishX")
    private String finishX;
    @Expose
    @SerializedName("finishY")
    private String finishY;
}
