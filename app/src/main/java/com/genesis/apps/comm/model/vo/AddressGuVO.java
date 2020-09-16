package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 주소 (구) 정보
 * @author hjpark
 * @see #gugunCd 구군코드
 * @see #gugunNm 지역명
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AddressGuVO extends BaseData {
    @Expose
    @SerializedName("gugunCd")
    private String gugunCd;
    @Expose
    @SerializedName("gugunNm")
    private String gugunNm;
}
