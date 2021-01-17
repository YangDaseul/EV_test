package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 소낙스 세차예약 내역
 * @author hjpark
 * @see #rsvtSeqNo    예약일련번호
 * @see #rsvtStusCd	예약상태코드      1000:  예약신청    2000 : 이용완료    9000 : 예약 취소
 * @see #rsvtDtm	예약일시          YYYYMMDDHH24MISS
 * @see #paymtWayCd	결제수단          1000: 현금결제
 * @see #paymtCost	결제금액
 * @see #cmpyCd	업체코드              소낙스  : SONAX
 * @see #brnhCd	지점코드
 * @see #brnhNm	지점명
 * @see #telNo	대표연락처            '-' 없음
 * @see #godsSeqNo	상품일련번호
 * @see #godsNm	상품명
 * @see #godsImgUri	상품이미지URI
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WashReserveVO extends BaseData {
    @Expose
    @SerializedName("rsvtSeqNo")
    private String rsvtSeqNo;
    @Expose
    @SerializedName("rsvtStusCd")
    private String rsvtStusCd;
    @Expose
    @SerializedName("rsvtDtm")
    private String rsvtDtm;
    @Expose
    @SerializedName("paymtWayCd")
    private String paymtWayCd;
    @Expose
    @SerializedName("paymtCost")
    private String paymtCost;
    @Expose
    @SerializedName("cmpyCd")
    private String cmpyCd;
    @Expose
    @SerializedName("brnhCd")
    private String brnhCd;
    @Expose
    @SerializedName("brnhNm")
    private String brnhNm;
    @Expose
    @SerializedName("telNo")
    private String telNo;
    @Expose
    @SerializedName("godsSeqNo")
    private String godsSeqNo;
    @Expose
    @SerializedName("godsNm")
    private String godsNm;
    @Expose
    @SerializedName("godsImgUri")
    private String godsImgUri;
    @Expose
    @SerializedName("dsctNm")
    private String dsctNm;
}
