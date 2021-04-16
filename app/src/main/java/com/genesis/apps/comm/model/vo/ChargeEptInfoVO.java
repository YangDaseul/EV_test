package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + E-PIT 충전소 목록 조회
 * @see #bid 충전사업자ID
 * @see #sid 충전소ID
 * @see #eid 환경부충전소ID
 * @see #chgName 충전소명
 * @see #dist 거리
 * @see #addr 주소
 * @see #lot 충전소-위도
 * @see #lat 충전소-경도
 * @see #opName 운영사업자명
 * @see #opTime 운영시간
 * @see #opTelNo 충전소전화번호
 * @see #chgStusCd 충전소상태
 * UNKNOWN 상태 불분명
 * OPEN 운영중
 * CLOSE 운영중지
 * READY 준비중
 * INACTIVE 운영중지
 * CHECKING 점검중
 * UNIDENTIFIED 상태미확인
 * @see #chgPrice 충전가격
 * @see #chgTypCd 충전소타입코드
 * GENESIS : 제네시스 전용 충전소
 * KIA : 기아 충전소
 * EPIT : E-Pit 충전소
 * @see #carPayUseYn 카페이사용가능여부
 * Y:  가능 N:불가
 * @see #superSpeedCnt 초고속충전기수
 * @see #highSpeedCnt 급속충전기수
 * @see #slowSpeedCnt 완속충전기수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargeEptInfoVO extends BaseData {
    @Expose
    @SerializedName("bid")
    private String bid;
    @Expose
    @SerializedName("sid")
    private String sid;
    @Expose
    @SerializedName("eid")
    private String eid;
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("addr")
    private String addr;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("opName")
    private String opName;
    @Expose
    @SerializedName("opTime")
    private String opTime;
    @Expose
    @SerializedName("opTelNo")
    private String opTelNo;
    @Expose
    @SerializedName("chgStusCd")
    private String chgStusCd;
    @Expose
    @SerializedName("chgPrice")
    private String chgPrice;
    @Expose
    @SerializedName("chgTypCd")
    private String chgTypCd;
    @Expose
    @SerializedName("carPayUseYn")
    private String carPayUseYn;
    @Expose
    @SerializedName("superSpeedCnt")
    private String superSpeedCnt;
    @Expose
    @SerializedName("highSpeedCnt")
    private String highSpeedCnt;
    @Expose
    @SerializedName("slowSpeedCnt")
    private String slowSpeedCnt;
} // end of class ChargeEptInfoVO
