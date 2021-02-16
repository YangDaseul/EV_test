package com.genesis.apps.comm.model.vo.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 고장코드 리스트
 * @author hjpark
 * @see #timestamp 차량 전송 시간 (YYYYMMDDHHmmSS)
 * @see #dtcType 고장 계열명
 * @see #description 고객 안내를 위한 설명
 * @see #dtcCnt 발생 건수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class DtcVO extends BaseData {
    @Expose
    @SerializedName("timestamp")
    private String timestamp;
    @Expose
    @SerializedName("dtcType")
    private String dtcType;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("dtcCnt")
    private float dtcCnt;
}