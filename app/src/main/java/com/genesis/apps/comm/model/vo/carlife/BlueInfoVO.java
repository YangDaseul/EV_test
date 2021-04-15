package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 충전 버틀러 블루멤버스 정보 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #blueSignInYN   블루멤버스 가입 여부
 * Y:가입, N:미가입
 *
 * @see #blueBalance    블루멤버스 잔여 포인트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BlueInfoVO extends BaseData {
    @Expose
    @SerializedName("blueSignInYN")
    private String blueSignInYN;
    @Expose
    @SerializedName("blueBalance")
    private int blueBalance;
}
