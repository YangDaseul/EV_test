package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 에스트래픽회원 정보 VO
 *
 * @author ljeun
 * @since 2021. 5. 11.
 *
 * @see #stcMbrYn   에스트래픽 회원가입여부
 * Y: 회원가입 N:미가입
 * @see #pwdYn  비밀번호 설정 여부
 * Y:  설정 N:미설정
 * @see #stcCardNo  선불교통카드번호
 * @see #stcCardUseYn   선불교통카드 사용가능 여부
 * Y: 사용가능 N:사용불가
 * @see #cretPnt    잔여 크레딧 포인트
 * @see #unpayYn    미수금 여부
 * Y:미수금발생 N:미수금없음
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class StcMbrInfoVO extends BaseData {
    @Expose
    @SerializedName("stcMbrYn")
    private String stcMbrYn;
    @Expose
    @SerializedName("pwdYn")
    private String pwdYn;
    @Expose
    @SerializedName("stcCardNo")
    private String stcCardNo;
    @Expose
    @SerializedName("stcCardUseYn")
    private String stcCardUseYn;
    @Expose
    @SerializedName("cretPnt")
    private String cretPnt;
    @Expose
    @SerializedName("unpayYn")
    private String unpayYn;
}
