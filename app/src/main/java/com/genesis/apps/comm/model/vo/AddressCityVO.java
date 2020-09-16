package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 주소 (시) 정보
 * @author hjpark
 * @see #sidoCd 시도코드
 * @see #sidoNm 시도명
 * @see #localNm 지역명
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AddressCityVO extends BaseData {
    @Expose
    @SerializedName("sidoCd")
    private String sidoCd;
    @Expose
    @SerializedName("sidoNm")
    private String sidoNm;
    @Expose
    @SerializedName("localNm")
    private String localNm;
}
