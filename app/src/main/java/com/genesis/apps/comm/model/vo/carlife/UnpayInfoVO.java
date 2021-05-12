package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 미수금정보 VO
 *
 * @author ljeun
 * @since 2021. 5. 11.
 *
 * @see #payTrxId   결제트랜잭션 ID
 * @see #roamNm 로밍사명 (선택)
 * @see #chgNm  충전소명
 * @see #sid    충전소 ID (선택)
 * @see #cid    충전기 ID (선택)
 * @see #chgQnty    충전량 (선택)
 * @see #unitPrice  적용단가 (선택)
 * @see #chgAmt 충전금액
 * @see #chgEndDtm  충전완료시간 YYYYMMDDHH24MISS (선택)
 * @see #payAmt 고객청구금액
 * @see #usedCretPnt    사용 크레딧 포인트트
 * */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class UnpayInfoVO extends BaseData {
    @Expose
    @SerializedName("payTrxId")
    private String payTrxId;
    @Expose
    @SerializedName("roamNm")
    private String roamNm;
    @Expose
    @SerializedName("chgNm")
    private String chgNm;
    @Expose
    @SerializedName("sid")
    private String sid;
    @Expose
    @SerializedName("cid")
    private String cid;
    @Expose
    @SerializedName("chgQnty")
    private String chgQnty;
    @Expose
    @SerializedName("unitPrice")
    private String unitPrice;
    @Expose
    @SerializedName("chgAmt")
    private String chgAmt;
    @Expose
    @SerializedName("chgEndDtm")
    private String chgEndDtm;
    @Expose
    @SerializedName("payAmt")
    private String payAmt;
    @Expose
    @SerializedName("usedCretPnt")
    private String usedCretPnt;
}
