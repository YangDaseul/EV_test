package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 원격진단신청정보
 * @author hjpark
 * @see #chckItemNm 점검항목코드명
 * ex)  01:배터리 02:엔진 03:브레이크 04:미션 05:TPMS
 * @see #chckItemRslt 점검항목결과명
 * ex)  'Y: 정상 N:점검필요
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RemoteCheckVO extends BaseData {
    @Expose
    @SerializedName("chckItemNm")
    private String chckItemNm;
    @Expose
    @SerializedName("chckItemRslt")
    private String chckItemRslt;
}
