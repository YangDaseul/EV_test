package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 동의수단
 * @author hjpark
 * @see #agreeTelNm 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreeSmsNm 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreeEmailNm 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreePostNm 약관구분코드
 * Y : 동의, 그외 : N
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AgreeMeansOilVO extends BaseData {
    @Expose
    @SerializedName("agreeTelNm")
    private String agreeTelNm;
    @Expose
    @SerializedName("agreeSmsNm")
    private String agreeSmsNm;
    @Expose
    @SerializedName("agreeEmailNm")
    private String agreeEmailNm;
    @Expose
    @SerializedName("agreePostNm")
    private String agreePostNm;
}
