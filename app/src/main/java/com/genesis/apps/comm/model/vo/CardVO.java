package com.genesis.apps.comm.model.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 카드정보
 * @author hjpark
 *
 * //바코드?에서 쓰는 정보
 * @see #isncCd 발급처코드
 * BLUE: 블루멤버스
 * HDOL : hyundai oilbank
 * GSCT  : GS 칼텍스
 * SOIL : S-OIL
 * SKNO : SK 이노베이션
 *
 *
 * //공통정보
 * @see #cardNo 카드번호
 *
 *
 *
 * //아래는 일단 블루멤버스 카드에서 사용하는 정보
 * @see #cardNm 카드명
 * @see #cardStusNm 카드상태명
 * 정상(10)/정지(20)/발급중(0)/소멸(30)
 * @see #cardClsNm 카드구분명
 * 신용카드(1)/비신용카드(2)
 * @see #cardKindNm 카드종류명
 * 현대가상화카드(10)/삼성페이(11)/삼성페이(12)/LG페이(13)/LG페이(14)
 * @see #cardIsncSubspDt 카드발급신청일자
 * YYYYMMDD (발급처코드가 BLUE 인 경우에만 필수)
 * @see #rgstYn 정유사 가입 여부
 * 정유사 카드인 경우에만 필수
 * Y:가입 N:미가입
 */


//@Entity(indices = {@Index(value = {"isncCd"}, unique = true)})
@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CardVO extends BaseData {

    public CardVO(String isncCd){
        this.isncCd = isncCd;
    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("isncCd")
    private String isncCd;

    @Expose
    @SerializedName("cardNo")
    private String cardNo;
    @Expose
    @SerializedName("cardNm")
    private String cardNm;
    @Expose
    @SerializedName("cardStusNm")
    private String cardStusNm;
    public static final String CARD_STATUS_10="발급완료";
    public static final String CARD_STATUS_20="정지";
    public static final String CARD_STATUS_30="소멸";
    public static final String CARD_STATUS_0="발급중";
    public static final String CARD_STATUS_99="추가"; //APP NATIVE에서만 사용하는 코드


    @Expose
    @SerializedName("cardClsNm")
    private String cardClsNm;
    @Expose
    @SerializedName("cardKindNm")
    private String cardKindNm;
    @Expose
    @SerializedName("cardIsncSubspDt")
    private String cardIsncSubspDt;
    @Expose
    @SerializedName("rgstYn")
    private String rgstYn;
    @Expose
    @SerializedName("errMsg")
    private String errMsg;

    private int orderNumber=9999;

    @Ignore
    private boolean isFavorite;
}
