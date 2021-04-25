package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 크레딧 정보
 * @author hjpark
 * @see #vin 차대번호
 * @see #mdlCd
 * @see #mdlNm
 * @see #balance 에스트래픽에서 조회한 크레딧포인트 잔액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CreditVO extends BaseData {
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("mdlCd")
    private String mdlCd;
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("balance")
    private String balance;
}
