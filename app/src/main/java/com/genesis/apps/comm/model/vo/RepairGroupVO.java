package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정비반 정보
 * @author hjpark
 * @see #rpshGrpCd 정비반코드
 * @see #rpshGrpNm 정비반명
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairGroupVO extends BaseData {
    @Expose
    @SerializedName("rpshGrpCd")
    private String rpshGrpCd;
    @Expose
    @SerializedName("rpshGrpNm")
    private String rpshGrpNm;

}
