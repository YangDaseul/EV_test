package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Odometers
 * @Brief 일별 누적 주행 거리
 */
public class Odometers extends BaseData {
    /**
     * @author hjpark
     * @brief Odometers 요청 항목
     * @see #startDate 오늘 -1 yyyyMMdd
     * @see #endDate  오늘 yyyyMMdd
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("startDate")
        private String startDate;

        @Expose
        @SerializedName("endDate")
        private String endDate;

        @Expose
        @SerializedName("carId")
        private String carId;

        public Request(String carId, String startDate, String endDate) {
            this.carId = carId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    /**
     * @author hjpark
     * @brief Odometers 응답 항목
     * @see #odometers 누적 운행 거리
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("odometers")
        private List<OdometerVO> odometers;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
