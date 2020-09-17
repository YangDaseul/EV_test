package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정비소 정보
 * @author hjpark
 * @see #asnCd 업체코드
 * @see #asnNm 업체명
 * @see #repTn 대표전화번호
 * @see #pbzAdr 사업장주소
 * @see #acps1Cd 지정정비공장구분코드
 * 일반블루핸즈 : ACPS1_CD = C 또는 D
 * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
 * @see #firmScnCd 정비망업체속성코드
 * FIRM_SCN_CD = 1 또는 4 : 제네시스전담
 * @see #mapXcooNm 지도X좌표
 * @see #mapYcooNm 지도Y좌표
 * @see #dist 거리(km)
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairVO extends BaseData {

    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("repTn")
    private String repTn;
    @Expose
    @SerializedName("pbzAdr")
    private String pbzAdr;
    @Expose
    @SerializedName("acps1Cd")
    private String acps1Cd;
    @Expose
    @SerializedName("firmScnCd")
    private String firmScnCd;
    @Expose
    @SerializedName("mapXcooNm")
    private String mapXcooNm;
    @Expose
    @SerializedName("mapYcooNm")
    private String mapYcooNm;
    @Expose
    @SerializedName("dist")
    private String dist;

}
