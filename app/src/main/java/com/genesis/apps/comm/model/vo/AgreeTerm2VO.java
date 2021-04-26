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
 * @see #termCd 약관구분코드
 * @see #termAgmtYn 약관동의여부
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AgreeTerm2VO extends BaseData {
    @Expose
    @SerializedName("termCd")
    private String termCd;
    @Expose
    @SerializedName("termAgmtYn")
    private String termAgmtYn;

}
