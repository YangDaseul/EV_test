package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author hjpark
 * @brief 응답 기본 항목
 * @see #rtCd 결과코드
 * @see #rtMsg 결과메세지
 */
public @Data
class BaseResponse extends BaseData {
    @Expose
    @SerializedName("rtCd")
    private String rtCd;
    @Expose
    @SerializedName("rtMsg")
    private String rtMsg;
}
