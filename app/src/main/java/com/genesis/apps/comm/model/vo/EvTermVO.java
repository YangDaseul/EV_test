package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 긴급충전출동
 * @author hjpark
 * @see #trmsAgmtYn 약관동의여부
 * 긴급충전출동 약관동의 여부 (Y: 동의 N:미동의)
 * @see #termList 약관리스트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class EvTermVO extends BaseData {
    @Expose
    @SerializedName("trmsAgmtYn")
    private String trmsAgmtYn;
    @Expose
    @SerializedName("termList")
    private List<TermVO> termList;
}
