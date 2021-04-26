package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @see #rgstDtm 등록일시
 * YYYYMMDDHH24MISS
 * @see #uid 사용자ID
 * id 마스킹
 * @see #uNm 사용자이름
 * 성명 마스킹
 * @see #starPoint 평점
 * @see #contents 내용
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReviewVO extends BaseData {
    @Expose
    @SerializedName("rgstDtm")
    private String rgstDtm;
    @Expose
    @SerializedName("uid")
    private String uid;
    @Expose
    @SerializedName("uNm")
    private String uNm;
    @Expose
    @SerializedName("starPoint")
    private String starPoint;
    @Expose
    @SerializedName("contents")
    private String contents;
} // end of class ReviewVO
