package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 크레딧 포인트 정보
 * @author hjpark
 * @see #chgName 차대번호
 * @see #elctcAmount 충전금액
 * @see #useCreditDate 포인트적립/사용일시
 * YYYYMMDDHH24MISS
 * @see #addCreditAmount 포인트적립금액
 * @see #seCreditAmount 포인트사용금액
 * @see #balanceAmount 포인트잔액
 * @see #divCd 구분코드
 *  - 01 : 적립  : 적립금액 > 0 경우
 *  - 02 : 사용  : 사용금액 > 0 경우
 *  - 09 : 취소  : 사용금액 < 0 경우
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CreditPointVO extends BaseData {
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("elctcAmount")
    private String elctcAmount;
    @Expose
    @SerializedName("useCreditDate")
    private String useCreditDate;
    @Expose
    @SerializedName("addCreditAmount")
    private String addCreditAmount;
    @Expose
    @SerializedName("seCreditAmount")
    private String seCreditAmount;
    @Expose
    @SerializedName("balanceAmount")
    private String balanceAmount;
    @Expose
    @SerializedName("divCd")
    private String divCd;
}
