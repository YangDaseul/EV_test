package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 기본 신청 정보 VO
 * @author ljeun
 * @since 2021. 4. 8.
 *
 * @see #bookingDtm 예약일시 YYYYMMDDHH24MISS
 * @see #keyTransferType 차량 키 전달 방식
 * KT_DKC : DKC
 * KT_BLL : 블루링크(픽업앤충전 사용하지 않늠)
 * KT_FOB : 실물키
 *
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReqInfoVO extends BaseData {
    @Expose
    @SerializedName("bookingDtm")
    private String bookingDtm;
    @Expose
    @SerializedName("keyTransferType")
    private String keyTransferType;
}
