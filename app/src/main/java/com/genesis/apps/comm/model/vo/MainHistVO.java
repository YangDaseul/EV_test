package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @Brief 차량 정보
 * @see #vrn 차량번호
 * @see #carGbNm 차종명
 * @see #drivDist 주행거리
 * 정비 차량 주행거리 (단위 km)
 * @see #mainNetNm 정비망명
 * @see #mainNetAddr 정비망주소
 * @see #mainPhone 정비망전화번호
 * @see #arrivDt 입고일자
 * 정비 입고일자 (형식 YYYY-MM-DD)
 * @see #mainHist 점검내역
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class MainHistVO extends BaseData {
    @Expose
    @SerializedName("vrn")
    private String vrn;
    @Expose
    @SerializedName("carGbNm")
    private String carGbNm;
    @Expose
    @SerializedName("drivDist")
    private String drivDist;
    @Expose
    @SerializedName("mainNetNm")
    private String mainNetNm;
    @Expose
    @SerializedName("mainNetAddr")
    private String mainNetAddr;
    @Expose
    @SerializedName("mainPhone")
    private String mainPhone;
    @Expose
    @SerializedName("arrivDt")
    private String arrivDt;
    @Expose
    @SerializedName("mainHist")
    private String mainHist;
}
