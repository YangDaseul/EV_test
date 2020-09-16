package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 약관정보
 * @author hjpark
 * @see #expnSeqNo 차계부일련번호
 * @see #vin 차대번호
 * @see #carRgstNo 차량번호
 * @see #mdlNm 차량모델명
 * @see #expnDivNm 지출분류명
 * 1000: 주유 2000:정비 3000:세차 4000:주차 5000:통행
 * 6000: 보험 7000:세금 8000:용품 9000:기타
 * @see #expnDtm 지출일시
 * @see #expnAmt 지출금액
 * @see #accmMilg 누적주행거리
 * 단위:km
 * @see #expnPlc 지출처
 * @see #rgstChnlNm 등록채널명
 * 1000: 본인등록 2000:XXXX
 * 6001: GS칼텍스
 *
 *
 *
 *
 *
 * @see #expnDivCd 지출분류코드
 * 1000: 주유 2000:정비 3000:세차 4000:주차 5000:통행
 * 6000: 보험 7000:세금 8000:용품 9000:기타
 * @see #rgstChnlCd 등록채널코드
 * 1000: 본인등록 2000:XXXX
 * 6001: GS칼텍스
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ExpnVO extends BaseData {
    @Expose
    @SerializedName("expnSeqNo")
    private String expnSeqNo;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("carRgstNo")
    private String carRgstNo;
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("expnDivNm")
    private String expnDivNm;
    @Expose
    @SerializedName("expnDtm")
    private String expnDtm;
    @Expose
    @SerializedName("expnAmt")
    private String expnAmt;
    @Expose
    @SerializedName("accmMilg")
    private String accmMilg;
    @Expose
    @SerializedName("expnPlc")
    private String expnPlc;
    @Expose
    @SerializedName("rgstChnlNm")
    private String rgstChnlNm;

    //아래는 1008/1006 에서 요청 시 사용
    @Expose
    @SerializedName("expnDivCd")
    private String expnDivCd;
    @Expose
    @SerializedName("rgstChnlCd")
    private String rgstChnlCd;
}
