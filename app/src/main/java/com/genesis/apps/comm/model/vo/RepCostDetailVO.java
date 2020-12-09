package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 대표가격
 * @author hjpark
 * @see #wkNm 작업명
 * @see #rprAmt 수리금액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepCostDetailVO extends BaseData {
    @Expose
    @SerializedName("wkNm")
    private String wkNm;
    @Expose
    @SerializedName("rprAmt")
    private String rprAmt;
}
