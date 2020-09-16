package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 버틀러 정보
 * @author hjpark
 * @see #cnsltSeqNo 상담일련번호
 * @see #cnsltQustDtm 상담문의일시
 * YYYYMMDDHH24MISS
 * @see #cnsltDivCd 상담구분코드
 * 구분코드 미정의 (예시 :  문의 / 예시…)
 * @see #cnsltTypCd 상담유형코드
 * 유형코드 미정의(예시: 정비/품질/….)
 * @see #cnsltStusCd 상담상태코드
 * 1000: 문의 ==> 2000 : 답변 ===> 3000 : 읽음
 * 2000 또는 3000 이면 답변완료
 * @see #ttl 제목
 * @see #cont 내용
 * @see #bltrAnswDtm bltrAnswDtm
 * YYYYMMDDHH24MISS
 * @see #cmntCont 댓글내용
 * 답변
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BtrCnslVO extends BaseData {
    @Expose
    @SerializedName("cnsltSeqNo")
    private String cnsltSeqNo;
    @Expose
    @SerializedName("cnsltQustDtm")
    private String cnsltQustDtm;
    @Expose
    @SerializedName("cnsltDivCd")
    private String cnsltDivCd;
    @Expose
    @SerializedName("cnsltTypCd")
    private String cnsltTypCd;
    @Expose
    @SerializedName("cnsltStusCd")
    private String cnsltStusCd;
    @Expose
    @SerializedName("ttl")
    private String ttl;
    @Expose
    @SerializedName("cont")
    private String cont;
    @Expose
    @SerializedName("bltrAnswDtm")
    private String bltrAnswDtm;
    @Expose
    @SerializedName("cmntCont")
    private String cmntCont;

}
