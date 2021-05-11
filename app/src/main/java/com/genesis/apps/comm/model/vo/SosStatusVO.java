package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 긴급충전출동
 * @author hjpark
 * @see #trmsAgmtYn 약관동의여부
 * 긴급충전출동 약관동의 여부 (Y: 동의 N:미동의)
 * @see #termList 약관리스트
 * @see #useYn 신청 가능 여부
 * 긴급충전출동 신규 신청 가능여부 (휴일, 인원부족과 같은경우 신규 접수 가능 여부)
 * @see #dataCount 잔여횟수
 * 긴급충전출동 고객명, 생년월일 또는 전화번호(미확정) 기준 EV 충전 잔여 횟수
 * @see #subspYn 신청여부
 * 긴급충전출동 신청 진행 중 여부 (Y: 진행중  N:진행중 없음)
 * @see #tmpAcptNo 가접수번호
 * 긴급충전출동 진행 중인 경우 가접수번호
 * @see #pgrsStusCd 진행상태코드
 * 긴급충전출동 가접수번호가 있을 경우 필수.
 * 진행상태 - (R:신청, -> W:접수,-> S:출동,-> E:완료, C:취소)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SosStatusVO extends BaseData {
//    @Expose
//    @SerializedName("trmsAgmtYn")
//    private String trmsAgmtYn;
    @Expose
    @SerializedName("useYn")
    private String useYn;
    @Expose
    @SerializedName("dataCount")
    private String dataCount;
    @Expose
    @SerializedName("subspYn")
    private String subspYn;
    @Expose
    @SerializedName("tmpAcptNo")
    private String tmpAcptNo;
    @Expose
    @SerializedName("pgrsStusCd")
    private String pgrsStusCd;
}
