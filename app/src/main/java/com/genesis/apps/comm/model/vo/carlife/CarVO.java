package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 차량 정보 VO
 * @author ljeun
 * @since 2021. 4. 8.
 *
 * @see #vin        차대번호
 * @see #carNo      차량번호(vrn)
 * @see #carCode    차종코드
 * @see #carName    차종명(선택)
 * @see #carColor   차량색상(선택)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CarVO extends BaseData {
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("carNo")
    private String carNo;
    @Expose
    @SerializedName("carCode")
    private String carCode;
    @Expose
    @SerializedName("carName")
    private String carName;
    @Expose
    @SerializedName("carColor")
    private String carColor;
}
