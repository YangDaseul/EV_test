package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 간편 결제 정보 VO
 *
 * @author ljeun
 * @since 2021. 5. 11.
 *
 * @see #signInYn   간편결제 회원가입여부
 * Y: 회원가입 N:미가입
 * @see #cardCount  결제 수단 갯수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PayInfoVO extends BaseData {
    @Expose
    @SerializedName("signInYn")
    private String signInYn;
    @Expose
    @SerializedName("cardCount")
    private String cardCount;
}
