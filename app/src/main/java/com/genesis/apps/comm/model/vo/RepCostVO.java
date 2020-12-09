package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief service + 정비소예약 - 대표가격조회
 * @author hjpark
 * @see #mdlNm 차종명
 * @see #costList 가격리스트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepCostVO extends BaseData {
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("costList")
    private List<RepCostDetailVO> costList;
}
