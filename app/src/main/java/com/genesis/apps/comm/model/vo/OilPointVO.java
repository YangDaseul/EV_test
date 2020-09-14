package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정유사 포인트 정보
 * @author hjpark
 * @see #oilRfnCd 정유사 코드
 * HDOL : hyundai oilbank
 * GSCT  : GS 칼텍스
 * SOIL : S-OIL
 * SKNO : SK 이노베이션
 * @see #oilRfnNm 정유사명
 * @see #rgstYn 가입여부
 * Y: 가입(연동) N:미가입(연동해제)  R:미가입(이전에 연동이력이 있는 경우)
 * @see #cardNo 카드번호
 * @see #pont 포인트
 * 앱에서 접속 가능한 URL
 * @see #errMsg 에러메시지
 * 정유사 연동시 오류가 발생시만 사용
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class OilPointVO extends BaseData {
    @Expose
    @SerializedName("oilRfnCd")
    private String oilRfnCd;
    public static final String OIL_CODE_HDOL="HDOL";
    public static final String OIL_CODE_GSCT="GSCT";
    public static final String OIL_CODE_SOIL="SOIL";
    public static final String OIL_CODE_SKNO="SKNO";

    @Expose
    @SerializedName("oilRfnNm")
    private String oilRfnNm;
    @Expose
    @SerializedName("rgstYn")
    private String rgstYn;
    public static final String OIL_JOIN_CODE_Y="Y";
    public static final String OIL_JOIN_CODE_N="N";
    public static final String OIL_JOIN_CODE_R="R";


    @Expose
    @SerializedName("cardNo")
    private String cardNo;
    @Expose
    @SerializedName("pont")
    private String pont;
    @Expose
    @SerializedName("errMsg")
    private String errMsg;
}
