package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 소모폼 교환 정보
 * @author hjpark
 * @see #partCd 소모품 코드
 * HSW 시스템 정의 코드
 * @see #partNm 소모품 명
 * HSW 시스템 정의 코드
 * @see #nextExchgDrivDist 다음 교환시점 주행거리(KM)
 * @see #exchgDrivDist 교환시점 주행거리(KM)
 * @see #alertYn 경고 여부
 * 경고 여부, 교환 시점 경고 여부 ?
 * 경고 : Y, 정상 : N
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PartVO extends BaseData {
    @Expose
    @SerializedName("partCd")
    private String partCd;
    @Expose
    @SerializedName("partNm")
    private String partNm;
    @Expose
    @SerializedName("nextExchgDrivDist")
    private String nextExchgDrivDist;
    @Expose
    @SerializedName("exchgDrivDist")
    private String exchgDrivDist;
    @Expose
    @SerializedName("alertYn")
    private String alertYn;
}
