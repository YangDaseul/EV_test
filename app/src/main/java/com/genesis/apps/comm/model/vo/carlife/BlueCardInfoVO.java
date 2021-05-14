package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 블루멤버스카드정보 VO
 * @author ljeun
 * @since 2021. 5. 11.
 *
 * @see #blueCardNo 블루멤버스카드번호
 * @see #cardIsncSubspDt    카드발급신청일자(YYYYMMDD)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BlueCardInfoVO extends BaseData {
    @Expose
    @SerializedName("blueCardNo")
    private String blueCardNo;
    @Expose
    @SerializedName("cardIsncSubspDt")
    private String cardIsncSubspDt;
}
