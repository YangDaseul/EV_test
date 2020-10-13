package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 상담유형/카테고리 정보
 * @author hjpark
 * @see #seqNo 순서
 * @see #catNm 카테고리명
 * 상담유형코드명/대분류코드명"  ex) 문의/품질
 * @see #conslDt 상담일자
 * YYYY.MM.DD ex) 2020.10.11
 * @see #conslTtl 제목
 * @see #conslCont 상담내용
 * @see #respDt 답변일자
 * YYYY.MM.DD ex) 2020.10.11
 * @see #respCont 답변내용
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CounselVO extends BaseData {
    @Expose
    @SerializedName("seqNo")
    private String seqNo;
    @Expose
    @SerializedName("catNm")
    private String catNm;
    @Expose
    @SerializedName("conslDt")
    private String conslDt;
    @Expose
    @SerializedName("conslTtl")
    private String conslTtl;
    @Expose
    @SerializedName("conslCont")
    private String conslCont;
    @Expose
    @SerializedName("respDt")
    private String respDt;
    @Expose
    @SerializedName("respCont")
    private String respCont;
}
