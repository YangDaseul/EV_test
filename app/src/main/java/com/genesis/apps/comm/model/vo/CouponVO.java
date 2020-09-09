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
 * @see #itemNm 항목명
 * 11:엔진오일 : 엔진오일, 오일필터, 에어클리너 필터
 * /13:에어컨필터 : 에어필터(실내)
 * /31:소모성부품 : 와이퍼 블레이드 어셈블리, 브레이크오일 교환 및 에어빼기, 앞 브레이크패드
 * /42:맵업데이트 : 좌동
 * /61:홈투홈 : 좌동
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
}
