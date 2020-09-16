package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정비 유형 정보
 * @author hjpark
 * @see #rparTypCd 정비유형코드
 * 부품계통코드
 * @see #rparTypNm 정비유형명
 * 부품계통명
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairTypeVO extends BaseData {
    @Expose
    @SerializedName("rparTypCd")
    private String rparTypCd;
    @Expose
    @SerializedName("rparTypNm")
    private String rparTypNm;

}
