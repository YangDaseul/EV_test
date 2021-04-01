package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 모빌리티케어 쿠폰 정보
 * @author hjpark
 * @see #itemDivCd 항목구분코드
 * 11.엔진오일세트 13:에어컨필터, 32:전방브레이크패드,33.와이퍼블레이드
 * 34:브레이크오일 61:픽업&딜리버리 99: 프리미엄 소낙스 세차 이용권
 * @see #itemNm 항목명
 * @see #totCnt 전체횟수
 * @see #remCnt 잔여횟수
 * @see #useCnt 사용횟수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CouponVO extends BaseData {
    @Expose
    @SerializedName("itemDivCd")
    private String itemDivCd;
    @Expose
    @SerializedName("itemNm")
    private String itemNm;

    @Expose
    @SerializedName("totCnt")
    private String totCnt;
    @Expose
    @SerializedName("remCnt")
    private String remCnt;
    @Expose
    @SerializedName("useCnt")
    private String useCnt;
    @Expose
    @SerializedName("itemDate")
    private String itemDate;
}
