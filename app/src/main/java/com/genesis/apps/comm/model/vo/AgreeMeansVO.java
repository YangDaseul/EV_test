package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 동의수단
 * @author hjpark
 * @see #agreeSms 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreeEmail 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreePost 약관구분코드
 * Y : 동의, 그외 : N
 * @see #agreeTel 약관구분코드
 * Y : 동의, 그외 : N
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AgreeMeansVO extends BaseData {
    @Expose
    @SerializedName("agreeSms")
    private String agreeSms;
    @Expose
    @SerializedName("agreeEmail")
    private String agreeEmail;
    @Expose
    @SerializedName("agreePost")
    private String agreePost;
    @Expose
    @SerializedName("agreeTel")
    private String agreeTel;
}
