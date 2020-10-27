package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief CCSP 사용자 정보
 * @author hjpark
 * @see #rgstYn 가입여부 Y: 가입  N: 미가입
 * @see #userId 사용자ID ex) cac8cbf9-7ae7-493e-87f0-bbffe623ebe4
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CcspVO extends BaseData {
    @Expose
    @SerializedName("rgstYn")
    private String rgstYn;
    @Expose
    @SerializedName("userId")
    private String userId;
}
