package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정비 현황 정보
 * @author hjpark
 * @see #asnCd 업체코드
 * @see #asnNm 업체명
 * @see #asnTelNo 대표전화번호
 * @see #asnAddr 사업장주소
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairVO extends BaseData {

    public RepairVO(){

    }

    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("asnTelNo")
    private String asnTelNo;
    @Expose
    @SerializedName("asnAddr")
    private String asnAddr;

    @Expose
    @SerializedName("wrhsNo")
    private String wrhsNo;
    @Expose
    @SerializedName("vhclInoutNo")
    private String vhclInoutNo;


    //adapter에서만 사용
    private String stusCd;
}
