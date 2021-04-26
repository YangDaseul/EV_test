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
 * @see #spid 환경부-기관ID
 * 환경부에서 발급한 ID
 * @see #csid 환경부-충전소ID
 * 환경부에서 발급한 ID
 * @see #espid EPIT-기관ID
 * @see #ecsid EPIT-충전소ID
 * @see #csnm 충전소명
 * @see #dist 거리
 * 단위 KM
 * @see #lat 충전소-위도
 * @see #lot 충전소-경도
 * @see #daddr 주소
 * 충전소 주소(도로명)
 * @see #addrDtl 상세주소
 * 상세 위치
 * @see #spnm 운영사업자명
 * E-PIT-기관명
 * @see #useYn 충전소운영여부
 * Y: 운영 N:미운영
 * @see #useTime 이용가능시간
 * @see #spcall 충전기문의연락처
 * @see #reservYn 예약가능여부
 * 환경부-기관ID = 'ST' (s-트래픽) 인경우에 'Y'
 * 그 외는 'N'
 * @see #gcpYn 카페이지원여부
 * Y: 카페이지원 N:카페이미지원
 * @see #genYn 제네시스전용충전소여부
 * Y: 제네시스전용충전소 N:제네시스전용충전소아님
 * @see #superSpeedCnt 초고속충전기수
 * @see #highSpeedCnt 급속충전기수
 * @see #slowSpeedCnt 완속충전기수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargeEptInfoVO extends BaseData {
    @Expose
    @SerializedName("spid")
    private String spid;
    @Expose
    @SerializedName("csid")
    private String csid;
    @Expose
    @SerializedName("espid")
    private String espid;
    @Expose
    @SerializedName("ecsid")
    private String ecsid;
    @Expose
    @SerializedName("csnm")
    private String csnm;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("daddr")
    private String daddr;
    @Expose
    @SerializedName("addrDtl")
    private String addrDtl;
    @Expose
    @SerializedName("spnm")
    private String spnm;
    @Expose
    @SerializedName("useYn")
    private String useYn;
    @Expose
    @SerializedName("useTime")
    private String useTime;
    @Expose
    @SerializedName("spcall")
    private String spcall;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
    @Expose
    @SerializedName("gcpYn")
    private String gcpYn;
    @Expose
    @SerializedName("genYn")
    private String genYn;
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
