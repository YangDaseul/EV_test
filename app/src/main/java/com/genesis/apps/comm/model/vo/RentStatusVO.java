package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 실운행자 내역 정보
 * @author hjpark
 *
 *
 * @see #seqNo 일련번호
 * @see #vin 차대번호
 * @see #rentPeriod 대여기간
 * 12, 24, 36, 48   99:기타
 * //신청상태
 * @see #ctrctNo 계약번호
 * @see #subspDtm 신청일시
 * 신청일시(yyyymmddhh24miss)
 * @see #aprvStusCd 승인상태코드
 * W : 승인요청대기, Y : 승인, N : 반려
 * @see #aprvDtm 승인/반려 일시
 * 승인/반려일시(yyyymmddhh24miss)
 * @see #rtnRsnMsg 반려사유메시지
 * 반려시 반려사유
 *
 * @see #csmrScnCd 고객구분코드
 * 1 : 개인(법인임직원)
 * 14 : 개인(리스/렌트 이용개인)
 * @see #asnCd 정비망코드
 * @see #asnNm 정비망명
 * @see #repTn 대표전화번호
 * @see #pbzAdr 사업장주소
 * @see #crdRcvScnCd 카드수령지구분코드
 * 1 : 자택  2 : 회사  3 : 기타
 * @see #crdRcvZip 카드수령지우편번호
 * @see #crdRcvAdr 카드수령지주소
 * @see #crdRcvDtlAdr 상세주소
 * @see #attachFilName 계약서파일명
 * @see #cnttUrl 계약서파일url
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RentStatusVO extends BaseData {

    //1007&1012 공통 (ctrctNo는 1012에 없음)
    @Expose
    @SerializedName("seqNo")
    private String seqNo;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("rentPeriod")
    private String rentPeriod;
    @Expose
    @SerializedName("ctrctNo")
    private String ctrctNo;
    @Expose
    @SerializedName("subspDtm")
    private String subspDtm;
    @Expose
    @SerializedName("aprvStusCd")
    private String aprvStusCd;
    @Expose
    @SerializedName("aprvDtm")
    private String aprvDtm;
    @Expose
    @SerializedName("rtnRsnMsg")
    private String rtnRsnMsg;


    //1012만 사용
    @Expose
    @SerializedName("csmrScnCd")
    private String csmrScnCd;
    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("repTn")
    private String repTn;
    @Expose
    @SerializedName("pbzAdr")
    private String pbzAdr;
    @Expose
    @SerializedName("crdRcvScnCd")
    private String crdRcvScnCd;
    @Expose
    @SerializedName("crdRcvZip")
    private String crdRcvZip;
    @Expose
    @SerializedName("crdRcvAdr")
    private String crdRcvAdr;
    @Expose
    @SerializedName("crdRcvDtlAdr")
    private String crdRcvDtlAdr;
    @Expose
    @SerializedName("attachFilName")
    private String attachFilName;
    @Expose
    @SerializedName("cnttUrl")
    private String cnttUrl;
}
