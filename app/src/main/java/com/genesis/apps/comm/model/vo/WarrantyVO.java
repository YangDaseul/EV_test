package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 모빌리티케어 쿠폰 정보
 * @author hjpark
 * @see #vin 차대번호
 * 요청시 차대번호를
 *  - 입력한 경우 : 입력한 차대번호
 *  - 입력하지 않은 경우 : 전체 소유차량 정보
 * @see #type 구분코드
 * 1010: 보증기간안내(일반보증기간)
 * 1011: 보증기간안내(배출가스관련보증기간)
 * @see #contents 보증기간안내
 * html 형식에 문자열
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WarrantyVO extends BaseData {
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("contents")
    private String contents;
}
