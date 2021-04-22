package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 에스트래픽 선불 정보 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #cardNo     선불권 카드 번호
 * @see #cardName   선불권 카드 이름
 * @see #availableYN    선불권 사용 가능 여부
 * @see #balance    선불권 잔액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class StrafficInfoVO extends BaseData {
    @Expose
    @SerializedName("cardNo")
    private String cardNo;
    @Expose
    @SerializedName("cardName")
    private String cardName;
    @Expose
    @SerializedName("availableYN")
    private String availableYN;
    @Expose
    @SerializedName("balance")
    private int balance;
}
