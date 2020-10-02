package com.genesis.apps.comm.model.vo.map;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.network.PlayMapRestApi;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 경로 탐색
 * @see #routeOption  탐색 옵션
 * (0: 최소시간, 1:(미정의) 2: 최단, 3:추천)
 * @see #feeOption  요금
 * (0: 모든도로, 1: 무료도로)
 * @see #roadOption  우선도로
 * (0: 모든도로, 1: 고속도로우선 2: 고속도로회피 3: 자동차전용도로회피)
 * @see #coordType 좌표계타입
 * (1: Bessel, 2: WGS84)
 * @see #carType 단말 차종
 * (0=미선택(기본값), 1=승용차/소형합차, 2= 중형승합차, 3= 대형승합차, 4= 대형화물차, 5= 특수화물차, 6=경차)
 * @see #startPoint 출발지 좌표
 * @see #viaPoint 경유지 좌표 리스트 (최대 3개)
 * @see #goalPoint 목적지 좌표
 * @see #findPathDataListener 경로탐색 결과에 대한 인터페이스 호출
 */
@EqualsAndHashCode(callSuper=false)
public @Data class FindPathReqVO extends BaseData {

    @Expose
    @SerializedName("routeOption")
    private String routeOption;
    @Expose
    @SerializedName("feeOption")
    private String feeOption;
    @Expose
    @SerializedName("roadOption")
    private String roadOption;
    @Expose
    @SerializedName("coordType")
    private String coordType;
    @Expose
    @SerializedName("carType")
    private String carType;
    @Expose
    @SerializedName("startPoint")
    private PlayMapPoint startPoint;
    @Expose
    @SerializedName("viaPoint")
    private ArrayList viaPoint;
    @Expose
    @SerializedName("goalPoint")
    private PlayMapPoint goalPoint;
    @Expose
    @SerializedName("findPathDataListener")
    private PlayMapRestApi.FindPathDataListenerCallback findPathDataListener;

}
