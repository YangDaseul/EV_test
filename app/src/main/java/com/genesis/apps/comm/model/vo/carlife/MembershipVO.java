package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 카라이프-충전버틀러 멤버십 정보 VO
 * @author ljeun
 * @since 2021. 4. 15.
 *
 * @see #membershipCode 멤버십 구분 코드
 * BLUEM: 블루멤버스 (추후 반영예정)
 * STRFF: 에스트래픽
 *
 * @see #membershipNo   멤버십 번호(선택)
 * @see #membershipUsePoint 멤버십 차감 포인트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class MembershipVO extends BaseData {
    @Expose
    @SerializedName("membershipCode")
    private String membershipCode;
    @Expose
    @SerializedName("membershipNo")
    private String membershipNo;
    @Expose
    @SerializedName("membershipUsePoint")
    private int membershipUsePoint;
}
