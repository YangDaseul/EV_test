package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 우편번호
 * @author hjpark
 * @see #zipNo 우편번호
 * @see #roadAddr 도로명주소
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AddressZipVO extends BaseData {
    @Expose
    @SerializedName("zipNo")
    private String zipNo;
    @Expose
    @SerializedName("roadAddr")
    private String roadAddr;
}
