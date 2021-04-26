package com.genesis.apps.comm.model.vo.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 소모품 리스트
 * @author hjpark
 * @see #sestCode 소모품 코드
 * 1 : 엔진오일/필터
 * 2 : 에어클리너
 * 3 : 에어컨 필터
 * 4 : 브레이크 패드
 * 9 : 브레이크 오일
 * ? : 냉각수
 * @see #sestName 소모품명
 * @see #stdDistance 교환 표준 주행 거리(단위: km)
 * @see #lastInfo 최종 교환 정보
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SestVO extends BaseData {
    @Expose
    @SerializedName("sestCode")
    private int sestCode;
    @Expose
    @SerializedName("sestName")
    private String sestName;
    @Expose
    @SerializedName("stdDistance")
    private float stdDistance;
    @Expose
    @SerializedName("lastInfo")
    private LastinfoVO lastInfo;
}
