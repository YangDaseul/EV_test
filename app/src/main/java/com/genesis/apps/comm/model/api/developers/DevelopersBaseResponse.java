package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author hjpark
 * @brief 응답 기본 항목
 * @see #errCode 에러코드
 * @see #errMsg 에러에 대한 상세 설명
 * @see #errId 에러 ID
 */
public @Data
class DevelopersBaseResponse extends BaseData {
    @Expose
    @SerializedName("errCode")
    private String errCode;
    @Expose
    @SerializedName("errMsg")
    private String errMsg;
    @Expose
    @SerializedName("errId")
    private String errId;
}
