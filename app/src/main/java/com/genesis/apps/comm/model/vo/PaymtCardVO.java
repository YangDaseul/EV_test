package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 간편 결제 수단 정보 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #cardType   결제 수단 코드
 * 신용카드 : C
 * 이음카드 : E
 * 휴대폰 : H
 *
 * @see #cardId     결제수단 ID(카드ID)
 * @see #cardCoCode 원천사코드
 * C001 : BC카드
 * C002 : KB카드
 * C003 : 하나카드
 * C004 : 삼성카드
 * C006 : 신한카드
 * C007 : 현대카드
 * C008 : 롯데카드
 * C012 : NH카드
 * PHON : 휴대폰
 *
 * @see #cardNo     카드번호
 * @see #cardName   카드명
 * @see #mainCardYN 주결제카드 여부
 * Y: 주카드, N:아님
 *
 * @see #registerDt 등록일 YYYYMMDDHH24MISS
 * @see #cardImageUrl   카드 이미지 URL
 * @see #lastUsedCardYN 최종결제카드 여부
 * Y:최종사용한 카드, N:아님
 *
 * @see #oneCardCode    가맹점대상 One카드
 * @see #plccYN     PLCC카드 여부
 * Y:PLCC카드, N:아님
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PaymtCardVO extends BaseData {
    @Expose
    @SerializedName("cardType")
    private String cardType;
    @Expose
    @SerializedName("cardId")
    private String cardId;
    @Expose
    @SerializedName("cardCoCode")
    private String cardCoCode;
    @Expose
    @SerializedName("cardNo")
    private String cardNo;
    @Expose
    @SerializedName("cardName")
    private String cardName;
    @Expose
    @SerializedName("mainCardYN")
    private String mainCardYN;
    @Expose
    @SerializedName("registerDt")
    private String registerDt;
    @Expose
    @SerializedName("cardImageUrl")
    private String cardImageUrl;
    @Expose
    @SerializedName("lastUsedCardYN")
    private String lastUsedCardYN;
    @Expose
    @SerializedName("oneCardCode")
    private String oneCardCode;
    @Expose
    @SerializedName("plccYN")
    private String plccYN;
}
