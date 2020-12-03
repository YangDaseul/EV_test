package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Detail
 * @Brief 안전운전점수 상세 조회
 */
public class Detail extends BaseData {
    /**
     * @author hjpark
     * @brief Detail 요청 항목
     * @see #carId 차량 고유 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("carId")
        private String carId;

        public Request(String carId) {
            this.carId = carId;
        }
    }

    /**
     * @author hjpark
     * @brief Detail 응답 항목
     * @see #safetyDrvScore 안전운전점수
     * @see #prevSafetyDrvScore 전일 안전운전점수
     * @see #isDiscountYn 보험할인 가능/불가 여부 (Y/N)
     * @see #bsrtAccCount 급가속 횟수
     * @see #bsrtDecCount 급감속 횟수
     * @see #nightDrvCount 심야운행 횟수
     * @see #rangeDrvDist 90일간 주행 거리
     * @see #distribution 전체 차량 대비 상위 n%
     * @see #modelDistribution 동일 차종 대비 상위 n%
     * @see #insightMsg 인사이트 메시지
     * @see #scoreDate 안전운전점수 업데이트일시
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("safetyDrvScore")
        private int safetyDrvScore;
        @Expose
        @SerializedName("prevSafetyDrvScore")
        private int prevSafetyDrvScore;
        @Expose
        @SerializedName("isDiscountYn")
        private String isDiscountYn;
        @Expose
        @SerializedName("bsrtAccCount")
        private int bsrtAccCount;
        @Expose
        @SerializedName("bsrtDecCount")
        private int bsrtDecCount;
        @Expose
        @SerializedName("nightDrvCount")
        private int nightDrvCount;
        @Expose
        @SerializedName("rangeDrvDist")
        private int rangeDrvDist;
        @Expose
        @SerializedName("distribution")
        private int distribution;
        @Expose
        @SerializedName("modelDistribution")
        private int modelDistribution;
        @Expose
        @SerializedName("insightMsg")
        private String insightMsg;
        @Expose
        @SerializedName("scoreDate")
        private String scoreDate;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
