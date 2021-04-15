package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 카라이프-충전버틀러 서비스 배정 기사 정보 VO
 *
 * @author ljeun
 * @since 2021. 4. 14.
 * @see #workerName 기사 이름
 * @see #workerHpNo 기사 전화번호
 * @see #workerType 수행 업무 구분
 * PKUP: 픽업 DLVR: 딜리버리 WASH: 세차 ECHG: EV충전 FULL: 전체
 *
 * @see #serviceViewLink    차량 상태 확인 링크
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WorkerVO extends BaseData {
    @Expose
    @SerializedName("workerName")
    private String workerName;
    @Expose
    @SerializedName("workerHpNo")
    private String workerHpNo;
    @Expose
    @SerializedName("workerType")
    private String workerType;
    @Expose
    @SerializedName("serviceViewLink")
    private String serviceViewLink;
}
