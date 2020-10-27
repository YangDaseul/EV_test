package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief service + 소낙스 세차이용권 선택
 * @author hjpark
 * @see #cmpyCd         업체코드                소낙스  : SONAX
 * @see #brnhCd	        지점코드
 * @see #brnhNm	        지점명
 * @see #brnhAddr	    지점주소
 * @see #telNo	        대표연락처              '-' 없음
 * @see #brnhX	        지점위치_X              위도 (예 : "33.494265")
 * @see #brnhY	        지점위치_Y              경도 (예: "126.702535")
 * @see #dist	        거리
 * @see #brnhIntro	    지점특장점(지점소개)     테이블 데이터의  "[전제]" 또는 "[일부]"는 APP에 보내지 않음
 * @see #brnhImgUri1	지점이미지Uri1
 * @see #brnhImgUri2	지점이미지Uri2
 * @see #brnhImgUri3	지점이미지Uri3
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WashBrnVO extends BaseData {
    @Expose
    @SerializedName("cmpyCd")
    private String cmpyCd;
    @Expose
    @SerializedName("brnhCd")
    private String brnhCd;
    @Expose
    @SerializedName("brnhNm")
    private String brnhNm;
    @Expose
    @SerializedName("brnhAddr")
    private String brnhAddr;
    @Expose
    @SerializedName("telNo")
    private String telNo;
    @Expose
    @SerializedName("brnhX")
    private String brnhX;
    @Expose
    @SerializedName("brnhY")
    private String brnhY;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("brnhIntro")
    private String brnhIntro;
    @Expose
    @SerializedName("brnhImgUri1")
    private String brnhImgUri1;
    @Expose
    @SerializedName("brnhImgUri2")
    private String brnhImgUri2;
    @Expose
    @SerializedName("brnhImgUri3")
    private String brnhImgUri3;
}
