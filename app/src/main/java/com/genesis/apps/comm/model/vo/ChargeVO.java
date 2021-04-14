package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전소 상세조회
 * @see #cid 충전기ID
 * @see #csupport 충전기지원타입
 * 100: 완속
 * 010: 급속
 * 001: 초급속
 * @see #stusCd 충전기상태코드
 * 0: 알수 없음
 * 1: 통신이상
 * 2: 충전대기
 * 3: 충전중
 * 4: 운영중지
 * 5: 점검중
 * 6: 예약중
 * 9: 상태 미확인
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargeVO extends BaseData {
    @Expose
    @SerializedName("cid")
    private String cid;
    @Expose
    @SerializedName("csupport")
    private String csupport;
    @Expose
    @SerializedName("stusCd")
    private String stusCd;
} // end of class ChargeVO
