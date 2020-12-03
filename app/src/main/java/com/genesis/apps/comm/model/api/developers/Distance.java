package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file ParkLocation
 * @Brief 최종 주차 위치 조회
 */
public class Distance extends BaseData {
    /**
     * @author hjpark
     * @brief ParkLocation 요청 항목
     * @see #carId 차량 고유 ID
     * @see #startDate 조회 시작일(YYYYMMDD)
     * @see #endDate 조회 종료일(YYYYMMDD)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {

        @Expose
        @SerializedName("carId")
        private String carId;
        @Expose
        @SerializedName("startDate")
        private String startDate;
        @Expose
        @SerializedName("endDate")
        private String endDate;

        public Request(String carId, String startDate, String endDate) {
            this.carId = carId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    /**
     * @author hjpark
     * @brief ParkLocation 응답 항목
     * @see #distances 일별 운행 거리 리스트
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    abstract
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("distances")
        private OdometerVO distances;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
