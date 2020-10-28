package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;




//CarWashHistory : 세차 예약 내역
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CarWashHistoryVO extends BaseData {

    @Expose
    @SerializedName("stringValue")
    private String stringValue;



}
