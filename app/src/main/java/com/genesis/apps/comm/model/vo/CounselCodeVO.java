package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 상담유형/카테고리 정보
 * @author hjpark
 * @see #cdValId 코드값ID
 * @see #cdValNm 코드값명
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CounselCodeVO extends BaseData {
    @Expose
    @SerializedName("cdValId")
    private String cdValId;
    @Expose
    @SerializedName("cdValNm")
    private String cdValNm;
}
