package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 차계부 금액 정보 (단위 : 원)
 * @author hjpark
 * @see #totUseAmt   총사용금액
 * @see #oilAmt	    주유/충전금액
 * @see #rparAmt	정비금액
 * @see #etcAmt	    기타금액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data

class ISTAmtVO extends BaseData {
    @Expose
    @SerializedName("totUseAmt")
    private String totUseAmt;
    @Expose
    @SerializedName("oilAmt")
    private String oilAmt;
    @Expose
    @SerializedName("rparAmt")
    private String rparAmt;
    @Expose
    @SerializedName("carWshAmt")
    private String carWshAmt;
    @Expose
    @SerializedName("etcAmt")
    private String etcAmt;

    //TODO 2021-04-25 PARK 인터페이스 설계서가 나오지 않아 임의로 추가한 데이터. 추후 변경 필요
    @Expose
    @SerializedName("chgCretAmt")
    private String chgCretAmt;
    @Expose
    @SerializedName("chgAmt")
    private String chgAmt;
}
