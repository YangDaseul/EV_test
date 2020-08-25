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
 * @see #carGbCd 결과메세지
 * 차량구분코드 (주차량/계약차량/디폴트차량)
 * 주차량: 0001, 계약차량: 0002, 디폴트차량: 0000
 * @see #vin 결과메세지
 * 차대 번호
 * 디폴트 차량인 경우 0000
 * @see #vrn 결과메세지
 * 차량 번호
 * @see #carMdelNm 결과메세지
 * 차량 모델 명
 * @see #carCdNm 결과메세지
 * 차량 옵션 명
 * @see #exteriorColor 결과메세지
 * 차량 외장 색상
 * @see #interiorColor 결과메세지
 * 차량 내장 색상
 * @see #contractNo 결과메세지
 * 차량 계약 번호
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CarVO extends BaseData {
    @Expose
    @SerializedName("carGbCd")
    private String carGbCd;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("vrn")
    private String vrn;
    @Expose
    @SerializedName("carMdelNm")
    private String carMdelNm;
    @Expose
    @SerializedName("carCdNm")
    private String carCdNm;
    @Expose
    @SerializedName("exteriorColor")
    private String exteriorColor;
    @Expose
    @SerializedName("interiorColor")
    private String interiorColor;
    @Expose
    @SerializedName("contractNo")
    private String contractNo;
}
